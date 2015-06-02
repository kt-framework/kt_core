package jp.kt.imagetool;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * Write out an image as a GIF.
 * <P>
 * <A HREF="/resources/classes/Acme/JPM/Encoders/GifEncoder.java">Fetch the
 * software.</A><BR>
 * <A HREF="/resources/classes/Acme.tar.gz">Fetch the entire Acme package.</A>
 * <P>
 *
 * @author tatsuya.kumon
 */
class GifImageDescriptorEncoder extends GifImageEncoder {
	private boolean interlace = false;

	private int delayTime = 0;

	// / Constructor from Image with interlace setting.
	// @param img The image to encode.
	// @param out The stream to write the GIF to.
	// @param interlace Whether to interlace.
	public GifImageDescriptorEncoder(BufferedImage img, OutputStream out,
			boolean interlace) throws IOException {
		super(img, out);
		this.interlace = interlace;
	}

	int width, height;
	int[][] rgbPixels;

	void encodeStart(int width, int height) throws IOException {
		this.width = width;
		this.height = height;
		rgbPixels = new int[height][width];
	}

	void encodePixels(int x, int y, int w, int h, int[] rgbPixels, int off,
			int scansize) throws IOException {
		// Save the pixels.
		for (int row = 0; row < h; ++row)
			System.arraycopy(rgbPixels, row * scansize + off, this.rgbPixels[y
					+ row], x, w);

	}

	// Acme.IntHashtable colorHash;
	GifIntHashtable colorHash;

	void encodeDone() throws IOException {
		int transparentIndex = -1;
		int transparentRgb = -1;
		// Put all the pixels into a hash table.
		colorHash = new GifIntHashtable();
		int index = 0;
		for (int row = 0; row < height; ++row) {
			for (int col = 0; col < width; ++col) {
				int rgb = rgbPixels[row][col];
				boolean isTransparent = ((rgb >>> 24) < 0x80);
				if (isTransparent) {
					if (transparentIndex < 0) {
						// First transparent color; remember it.
						transparentIndex = index;
						transparentRgb = rgb;
					} else if (rgb != transparentRgb) {
						// A second transparent color; replace it with
						// the first one.
						rgbPixels[row][col] = rgb = transparentRgb;
					}
				}
				GifEncoderHashitem item = (GifEncoderHashitem) colorHash
						.get(rgb);
				if (item == null) {
					if (index >= 256)
						throw new IOException("too many colors for a GIF");
					item = new GifEncoderHashitem(rgb, 1, index, isTransparent);
					++index;
					colorHash.put(rgb, item);
				} else
					++item.count;
			}
		}

		// Figure out how many bits to use.
		int logColors;
		if (index <= 2)
			logColors = 1;
		else if (index <= 4)
			logColors = 2;
		else if (index <= 16)
			logColors = 4;
		else
			logColors = 8;

		// Turn colors into colormap entries.
		int mapSize = 1 << logColors;
		byte[] reds = new byte[mapSize];
		byte[] grns = new byte[mapSize];
		byte[] blus = new byte[mapSize];
		for (Enumeration<Object> e = colorHash.elements(); e.hasMoreElements();) {
			GifEncoderHashitem item = (GifEncoderHashitem) e.nextElement();
			reds[item.index] = (byte) ((item.rgb >> 16) & 0xff);
			grns[item.index] = (byte) ((item.rgb >> 8) & 0xff);
			blus[item.index] = (byte) (item.rgb & 0xff);
		}

		gifEncode(out, width, height, interlace, (byte) 0, transparentIndex,
				logColors, reds, grns, blus);
	}

	byte getPixel(int x, int y) throws IOException {
		GifEncoderHashitem item = (GifEncoderHashitem) colorHash
				.get(rgbPixels[y][x]);
		if (item == null)
			throw new IOException("color not found");
		return (byte) item.index;
	}

	static void writeString(OutputStream out, String str) throws IOException {
		byte[] buf = str.getBytes();
		out.write(buf);
	}

	// Adapted from ppmtogif, which is based on GIFENCOD by David
	// Rowley <mgardi@watdscu.waterloo.edu>. Lempel-Zim compression
	// based on "compress".

	int width2, height2;
	boolean interlace2;
	int curx, cury;
	int countDown;
	int pass = 0;

	void gifEncode(OutputStream outs, int width2, int height2,
			boolean interlace2, byte background, int transparent,
			int bitsPerPixel, byte[] red, byte[] green, byte[] blue)
			throws IOException {
		int colorMapSize;
		int initCodeSize;
		int i;

		this.width2 = width2;
		this.height2 = height2;
		this.interlace2 = interlace2;
		colorMapSize = 1 << bitsPerPixel;

		// Calculate number of bits we are expecting
		countDown = width2 * height2;

		// Indicate which pass we are on (if interlace)
		pass = 0;

		// The initial code size
		if (bitsPerPixel <= 1) {
			initCodeSize = 2;
		} else {
			initCodeSize = bitsPerPixel;
		}

		// Set up the current x and y position
		curx = 0;
		cury = 0;

		// Write out extension for transparent colour index, if necessary.
		if (transparent != -1) {
			putbyte((byte) '!', outs);
			putbyte((byte) 0xf9, outs);
			putbyte((byte) 4, outs);
			putbyte((byte) (((0x7 & 0) << 2) | 0x1), outs);
			putbyte((byte) (0xff & getDelayTime()), outs);
			putbyte((byte) (0xff & (getDelayTime() >> 8)), outs);
			putbyte((byte) transparent, outs);
			putbyte((byte) 0, outs);
		} else {
			putbyte((byte) '!', outs);
			putbyte((byte) 0xf9, outs);
			putbyte((byte) 4, outs);
			putbyte((byte) (((0x7 & 0) << 2)), outs);
			putbyte((byte) (0xff & getDelayTime()), outs);
			putbyte((byte) (0xff & (getDelayTime() >> 8)), outs);
			putbyte((byte) 0, outs);
			putbyte((byte) 0, outs);
		}

		// Write an Image separator
		putbyte((byte) ',', outs);

		// Write the Image header
		putword(0, outs);
		putword(0, outs);
		putword(width2, outs);
		putword(height2, outs);

		// 1bitローカルカラーテーブルを使用
		// 6-8bitローカルカラーテーブルの大きさを指定

		// Write out whether or not the image is interlaced
		if (interlace2)
			putbyte((byte) (0xc0 | (0x7 & (bitsPerPixel - 1))), outs);
		else
			putbyte((byte) (0x80 | (0x7 & (bitsPerPixel - 1))), outs);

		// Write out the Global Colour Map
		for (i = 0; i < colorMapSize; ++i) {
			putbyte(red[i], outs);
			putbyte(green[i], outs);
			putbyte(blue[i], outs);
		}

		// Write out the initial code size
		putbyte((byte) initCodeSize, outs);

		// Go and actually compress the data
		compress(initCodeSize + 1, outs);

		// Write out a Zero-length packet (to end the series)
		putbyte((byte) 0, outs);
	}

	// Bump the 'curx' and 'cury' to point to the next pixel
	void bumpPixel() {
		// Bump the current X position
		++curx;

		// If we are at the end of a scan line, set curx back to the beginning
		// If we are interlaced, bump the cury to the appropriate spot,
		// otherwise, just increment it.
		if (curx == width2) {
			curx = 0;

			if (!interlace2)
				++cury;
			else {
				switch (pass) {
				case 0:
					cury += 8;
					if (cury >= height2) {
						++pass;
						cury = 4;
					}
					break;

				case 1:
					cury += 8;
					if (cury >= height2) {
						++pass;
						cury = 2;
					}
					break;

				case 2:
					cury += 4;
					if (cury >= height2) {
						++pass;
						cury = 1;
					}
					break;

				case 3:
					cury += 2;
					break;
				}
			}
		}
	}

	static final int EOF = -1;

	// Return the next pixel from the image
	int gifNextPixel() throws IOException {
		byte r;

		if (countDown == 0)
			return EOF;

		--countDown;

		r = getPixel(curx, cury);

		bumpPixel();

		return r & 0xff;
	}

	// Write out a word to the GIF file
	void putword(int w, OutputStream outs) throws IOException {
		putbyte((byte) (w & 0xff), outs);
		putbyte((byte) ((w >> 8) & 0xff), outs);
	}

	// Write out a byte to the GIF file
	void putbyte(byte b, OutputStream outs) throws IOException {
		outs.write(b);
	}

	// GIFCOMPR.C - GIF Image compression routines
	//
	// Lempel-Ziv compression based on 'compress'. GIF modifications by
	// David Rowley (mgardi@watdcsu.waterloo.edu)

	// General DEFINEs

	static final int BITS = 12;

	static final int HSIZE = 5003; // 80% occupancy

	// GIF Image compression - modified 'compress'
	//
	// Based on: compress.c - File compression ala IEEE Computer, June 1984.
	//
	// By Authors: Spencer W. Thomas (decvax!harpo!utah-cs!utah-gr!thomas)
	// Jim McKie (decvax!mcvax!jim)
	// Steve Davies (decvax!vax135!petsd!peora!srd)
	// Ken Turkowski (decvax!decwrl!turtlevax!ken)
	// James A. Woods (decvax!ihnp4!ames!jaw)
	// Joe Orost (decvax!vax135!petsd!joe)

	int nBits; // number of bits/code
	int maxbits = BITS; // user settable max # bits/code
	int maxcode; // maximum code, given n_bits
	int maxmaxcode = 1 << BITS; // should NEVER generate this code

	final int MAXCODE(int n_bits) {
		return (1 << n_bits) - 1;
	}

	int[] htab = new int[HSIZE];
	int[] codetab = new int[HSIZE];

	int hsize = HSIZE; // for dynamic table sizing

	int freeEnt = 0; // first unused entry

	// block compression parameters -- after all codes are used up,
	// and compression rate changes, start over.
	boolean clearFlg = false;

	// Algorithm: use open addressing double hashing (no chaining) on the
	// prefix code / next character combination. We do a variant of Knuth's
	// algorithm D (vol. 3, sec. 6.4) along with G. Knott's relatively-prime
	// secondary probe. Here, the modular division first probe is gives way
	// to a faster exclusive-or manipulation. Also do block compression with
	// an adaptive reset, whereby the code table is cleared when the compression
	// ratio decreases, but after the table fills. The variable-length output
	// codes are re-sized at this point, and a special CLEAR code is generated
	// for the decompressor. Late addition: construct the table according to
	// file size for noticeable speed improvement on small files. Please direct
	// questions about this implementation to ames!jaw.

	int gInitBits;

	int clearCode;
	int eofCode;

	void compress(int init_bits, OutputStream outs) throws IOException {
		int fcode;
		int i /* = 0 */;
		int c;
		int ent;
		int disp;
		int hsize_reg;
		int hshift;

		// Set up the globals: g_init_bits - initial number of bits
		gInitBits = init_bits;

		// Set up the necessary values
		clearFlg = false;
		nBits = gInitBits;
		maxcode = MAXCODE(nBits);

		clearCode = 1 << (init_bits - 1);
		eofCode = clearCode + 1;
		freeEnt = clearCode + 2;

		charInit();

		ent = gifNextPixel();

		hshift = 0;
		for (fcode = hsize; fcode < 65536; fcode *= 2)
			++hshift;
		hshift = 8 - hshift; // set hash code range bound

		hsize_reg = hsize;
		clHash(hsize_reg); // clear hash table

		output(clearCode, outs);

		outer_loop: while ((c = gifNextPixel()) != EOF) {
			fcode = (c << maxbits) + ent;
			i = (c << hshift) ^ ent; // xor hashing

			if (htab[i] == fcode) {
				ent = codetab[i];
				continue;
			} else if (htab[i] >= 0) // non-empty slot
			{
				disp = hsize_reg - i; // secondary hash (after G. Knott)
				if (i == 0)
					disp = 1;
				do {
					if ((i -= disp) < 0)
						i += hsize_reg;

					if (htab[i] == fcode) {
						ent = codetab[i];
						continue outer_loop;
					}
				} while (htab[i] >= 0);
			}
			output(ent, outs);
			ent = c;
			if (freeEnt < maxmaxcode) {
				codetab[i] = freeEnt++; // code -> hashtable
				htab[i] = fcode;
			} else
				clBlock(outs);
		}
		// Put out the final code.
		output(ent, outs);
		output(eofCode, outs);
	}

	// output
	//
	// Output the given code.
	// Inputs:
	// code: A n_bits-bit integer. If == -1, then EOF. This assumes
	// that n_bits =< wordsize - 1.
	// Outputs:
	// Outputs code to the file.
	// Assumptions:
	// Chars are 8 bits long.
	// Algorithm:
	// Maintain a BITS character long buffer (so that 8 codes will
	// fit in it exactly). Use the VAX insv instruction to insert each
	// code in turn. When the buffer fills up empty it and start over.

	int curAccum = 0;
	int curBits = 0;

	int masks[] = { 0x0000, 0x0001, 0x0003, 0x0007, 0x000F, 0x001F, 0x003F,
			0x007F, 0x00FF, 0x01FF, 0x03FF, 0x07FF, 0x0FFF, 0x1FFF, 0x3FFF,
			0x7FFF, 0xFFFF };

	void output(int code, OutputStream outs) throws IOException {
		curAccum &= masks[curBits];

		if (curBits > 0)
			curAccum |= (code << curBits);
		else
			curAccum = code;

		curBits += nBits;

		while (curBits >= 8) {
			charOut((byte) (curAccum & 0xff), outs);
			curAccum >>= 8;
			curBits -= 8;
		}

		// If the next entry is going to be too big for the code size,
		// then increase it, if possible.
		if (freeEnt > maxcode || clearFlg) {
			if (clearFlg) {
				maxcode = MAXCODE(nBits = gInitBits);
				clearFlg = false;
			} else {
				++nBits;
				if (nBits == maxbits)
					maxcode = maxmaxcode;
				else
					maxcode = MAXCODE(nBits);
			}
		}

		if (code == eofCode) {
			// At EOF, write the rest of the buffer.
			while (curBits > 0) {
				charOut((byte) (curAccum & 0xff), outs);
				curAccum >>= 8;
				curBits -= 8;
			}

			flushChar(outs);
		}
	}

	// Clear out the hash table

	// table clear for block compress
	void clBlock(OutputStream outs) throws IOException {
		clHash(hsize);
		freeEnt = clearCode + 2;
		clearFlg = true;

		output(clearCode, outs);
	}

	// reset code table
	void clHash(int hsize) {
		for (int i = 0; i < hsize; ++i)
			htab[i] = -1;
	}

	// GIF Specific routines

	// Number of characters so far in this 'packet'
	int aCount;

	// Set up the 'byte output' routine
	void charInit() {
		aCount = 0;
	}

	// Define the storage for the packet accumulator
	byte[] accum = new byte[256];

	// Add a character to the end of the current packet, and if it is 254
	// characters, flush the packet to disk.
	void charOut(byte c, OutputStream outs) throws IOException {
		accum[aCount++] = c;
		if (aCount >= 254)
			flushChar(outs);
	}

	// Flush the packet to disk, and reset the accumulator
	void flushChar(OutputStream outs) throws IOException {
		if (aCount > 0) {
			outs.write(aCount);
			outs.write(accum, 0, aCount);
			aCount = 0;
		}
	}

	/**
	 * DelayTimeを取得する.
	 *
	 * @return delayTime
	 */
	public int getDelayTime() {
		return delayTime;
	}

	/**
	 * DelayTimeをセットする.
	 *
	 * @param delayTime
	 *            DelayTime
	 */
	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}
}

class GifEncoderHashitem {
	public int rgb;
	public int count;
	public int index;
	public boolean isTransparent;

	public GifEncoderHashitem(int rgb, int count, int index,
			boolean isTransparent) {
		this.rgb = rgb;
		this.count = count;
		this.index = index;
		this.isTransparent = isTransparent;
	}
}
