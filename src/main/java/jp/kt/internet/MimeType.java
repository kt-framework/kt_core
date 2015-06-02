package jp.kt.internet;

import java.util.HashMap;
import java.util.Map;

import jp.kt.tool.Validator;

/**
 * MimeTypeの管理クラス.
 * <p>
 * 以下の物が取得可能です。
 * <table summary="MIMEタイプ一覧" border="1">
 * <tr>
 * <th>MIME Media Type</th>
 * <th>拡張子</th>
 * </tr>
 * <tr>
 * <td>application/andrew-inset</td>
 * <td>ez&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/atom</td>
 * <td>atom&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/atomcat+xml</td>
 * <td>atomcat&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/atomserv+xml</td>
 * <td>atomsrv&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/cap</td>
 * <td>cap&nbsp;pcap&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/cu-seeme</td>
 * <td>cu&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/dsptype</td>
 * <td>tsp&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/futuresplash</td>
 * <td>spl&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/hta</td>
 * <td>hta&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/java-archive</td>
 * <td>jar&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/java-serialized-object</td>
 * <td>ser&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/java-vm</td>
 * <td>class&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/mac-binhex40</td>
 * <td>hqx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/mac-compactpro</td>
 * <td>cpt&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/mathematica</td>
 * <td>nb&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/msaccess</td>
 * <td>mdb&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/msword</td>
 * <td>doc&nbsp;dot&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/octet-stream</td>
 * <td>bin&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/oda</td>
 * <td>oda&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/ogg</td>
 * <td>ogg&nbsp;ogx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/pdf</td>
 * <td>pdf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/pgp-keys</td>
 * <td>key&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/pgp-signature</td>
 * <td>pgp&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/pics-rules</td>
 * <td>prf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/postscript</td>
 * <td>ps&nbsp;ai&nbsp;eps&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/rar</td>
 * <td>rar&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/rdf+xml</td>
 * <td>rdf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/rss+xml</td>
 * <td>rss&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/rtf</td>
 * <td>rtf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/smil</td>
 * <td>smi&nbsp;smil&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/wordperfect</td>
 * <td>wpd&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/wordperfect5.1</td>
 * <td>wp5&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/xhtml+xml</td>
 * <td>xhtml&nbsp;xht&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/xml</td>
 * <td>xml&nbsp;xsl&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/zip</td>
 * <td>zip&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.cinderella</td>
 * <td>cdy&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.google-earth.kml+xml</td>
 * <td>kml&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.google-earth.kmz</td>
 * <td>kmz&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.mozilla.xul+xml</td>
 * <td>xul&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.ms-excel</td>
 * <td>xls&nbsp;xlb&nbsp;xlt&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.ms-pki.seccat</td>
 * <td>cat&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.ms-pki.stl</td>
 * <td>stl&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.ms-powerpoint</td>
 * <td>ppt&nbsp;pps&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.oasis.opendocument.chart</td>
 * <td>odc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.oasis.opendocument.database</td>
 * <td>odb&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.oasis.opendocument.formula</td>
 * <td>odf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.oasis.opendocument.graphics</td>
 * <td>odg&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.oasis.opendocument.graphics-template</td>
 * <td>otg&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.oasis.opendocument.image</td>
 * <td>odi&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.oasis.opendocument.presentation</td>
 * <td>odp&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.oasis.opendocument.presentation-template</td>
 * <td>otp&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.oasis.opendocument.spreadsheet</td>
 * <td>ods&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.oasis.opendocument.spreadsheet-template</td>
 * <td>ots&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.oasis.opendocument.text</td>
 * <td>odt&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.oasis.opendocument.text-master</td>
 * <td>odm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.oasis.opendocument.text-template</td>
 * <td>ott&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.oasis.opendocument.text-web</td>
 * <td>oth&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.rim.cod</td>
 * <td>cod&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.smaf</td>
 * <td>mmf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.stardivision.calc</td>
 * <td>sdc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.stardivision.chart</td>
 * <td>sds&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.stardivision.draw</td>
 * <td>sda&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.stardivision.impress</td>
 * <td>sdd&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.stardivision.math</td>
 * <td>sdf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.stardivision.writer</td>
 * <td>sdw&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.stardivision.writer-global</td>
 * <td>sgl&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.sun.xml.calc</td>
 * <td>sxc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.sun.xml.calc.template</td>
 * <td>stc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.sun.xml.draw</td>
 * <td>sxd&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.sun.xml.draw.template</td>
 * <td>std&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.sun.xml.impress</td>
 * <td>sxi&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.sun.xml.impress.template</td>
 * <td>sti&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.sun.xml.math</td>
 * <td>sxm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.sun.xml.writer</td>
 * <td>sxw&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.sun.xml.writer.global</td>
 * <td>sxg&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.sun.xml.writer.template</td>
 * <td>stw&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.symbian.install</td>
 * <td>sis&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.visio</td>
 * <td>vsd&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.wap.wbxml</td>
 * <td>wbxml&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.wap.wmlc</td>
 * <td>wmlc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/vnd.wap.wmlscriptc</td>
 * <td>wmlsc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-123</td>
 * <td>wk&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-7z-compressed</td>
 * <td>7z&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-abiword</td>
 * <td>abw&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-apple-diskimage</td>
 * <td>dmg&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-bcpio</td>
 * <td>bcpio&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-bittorrent</td>
 * <td>torrent&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-cab</td>
 * <td>cab&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-cbr</td>
 * <td>cbr&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-cbz</td>
 * <td>cbz&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-cdf</td>
 * <td>cdf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-cdlink</td>
 * <td>vcd&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-chess-pgn</td>
 * <td>pgn&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-cpio</td>
 * <td>cpio&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-csh</td>
 * <td>csh&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-debian-package</td>
 * <td>deb&nbsp;udeb&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-director</td>
 * <td>dcr&nbsp;dir&nbsp;dxr&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-dms</td>
 * <td>dms&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-doom</td>
 * <td>wad&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-dvi</td>
 * <td>dvi&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-httpd-eruby</td>
 * <td>rhtml&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-flac</td>
 * <td>flac&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-font</td>
 * <td>pfa&nbsp;pfb&nbsp;gsf&nbsp;pcf&nbsp;pcf.Z&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-freemind</td>
 * <td>mm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-futuresplash</td>
 * <td>spl&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-gnumeric</td>
 * <td>gnumeric&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-go-sgf</td>
 * <td>sgf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-graphing-calculator</td>
 * <td>gcf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-gtar</td>
 * <td>gtar&nbsp;tgz&nbsp;taz&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-hdf</td>
 * <td>hdf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-httpd-php</td>
 * <td>phtml&nbsp;pht&nbsp;php&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-httpd-php-source</td>
 * <td>phps&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-httpd-php3</td>
 * <td>php3&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-httpd-php3-preprocessed</td>
 * <td>php3p&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-httpd-php4</td>
 * <td>php4&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-ica</td>
 * <td>ica&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-internet-signup</td>
 * <td>ins&nbsp;isp&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-iphone</td>
 * <td>iii&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-iso9660-image</td>
 * <td>iso&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-java-jnlp-file</td>
 * <td>jnlp&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-javascript</td>
 * <td>js&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-jmol</td>
 * <td>jmz&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-kchart</td>
 * <td>chrt&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-killustrator</td>
 * <td>kil&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-koan</td>
 * <td>skp&nbsp;skd&nbsp;skt&nbsp;skm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-kpresenter</td>
 * <td>kpr&nbsp;kpt&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-kspread</td>
 * <td>ksp&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-kword</td>
 * <td>kwd&nbsp;kwt&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-latex</td>
 * <td>latex&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-lha</td>
 * <td>lha&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-lyx</td>
 * <td>lyx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-lzh</td>
 * <td>lzh&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-lzx</td>
 * <td>lzx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-maker</td>
 * <td>frm&nbsp;maker&nbsp;frame&nbsp;fm&nbsp;fb&nbsp;book&nbsp;fbdoc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-mif</td>
 * <td>mif&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-ms-wmd</td>
 * <td>wmd&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-ms-wmz</td>
 * <td>wmz&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-msdos-program</td>
 * <td>com&nbsp;exe&nbsp;bat&nbsp;dll&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-msi</td>
 * <td>msi&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-netcdf</td>
 * <td>nc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-ns-proxy-autoconfig</td>
 * <td>pac&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-nwc</td>
 * <td>nwc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-object</td>
 * <td>o&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-oz-application</td>
 * <td>oza&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-pkcs7-certreqresp</td>
 * <td>p7r&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-pkcs7-crl</td>
 * <td>crl&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-python-code</td>
 * <td>pyc&nbsp;pyo&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-quicktimeplayer</td>
 * <td>qtl&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-redhat-package-manager</td>
 * <td>rpm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-sh</td>
 * <td>sh&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-shar</td>
 * <td>shar&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-shockwave-flash</td>
 * <td>swf&nbsp;swfl&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-stuffit</td>
 * <td>sit&nbsp;sitx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-sv4cpio</td>
 * <td>sv4cpio&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-sv4crc</td>
 * <td>sv4crc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-tar</td>
 * <td>tar&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-tcl</td>
 * <td>tcl&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-tex-gf</td>
 * <td>gf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-tex-pk</td>
 * <td>pk&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-texinfo</td>
 * <td>texinfo&nbsp;texi&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-trash</td>
 * <td>~&nbsp;%&nbsp;bak&nbsp;old&nbsp;sik&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-troff</td>
 * <td>t&nbsp;tr&nbsp;roff&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-troff-man</td>
 * <td>man&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-troff-me</td>
 * <td>me&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-troff-ms</td>
 * <td>ms&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-ustar</td>
 * <td>ustar&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-wais-source</td>
 * <td>src&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-wingz</td>
 * <td>wz&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-x509-ca-cert</td>
 * <td>crt&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-xcf</td>
 * <td>xcf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-xfig</td>
 * <td>fig&nbsp;</td>
 * </tr>
 * <tr>
 * <td>application/x-xpinstall</td>
 * <td>xpi&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/basic</td>
 * <td>au&nbsp;snd&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/midi</td>
 * <td>mid&nbsp;midi&nbsp;kar&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/mpeg</td>
 * <td>mpga&nbsp;mpega&nbsp;mp2&nbsp;mp3&nbsp;m4a&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/mpegurl</td>
 * <td>m3u&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/ogg</td>
 * <td>oga&nbsp;spx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/prs.sid</td>
 * <td>sid&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/x-aiff</td>
 * <td>aif&nbsp;aiff&nbsp;aifc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/x-gsm</td>
 * <td>gsm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/x-mpegurl</td>
 * <td>m3u&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/x-ms-wma</td>
 * <td>wma&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/x-ms-wax</td>
 * <td>wax&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/x-pn-realaudio</td>
 * <td>ra&nbsp;rm&nbsp;ram&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/x-realaudio</td>
 * <td>ra&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/x-scpls</td>
 * <td>pls&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/x-sd2</td>
 * <td>sd2&nbsp;</td>
 * </tr>
 * <tr>
 * <td>audio/x-wav</td>
 * <td>wav&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-alchemy</td>
 * <td>alc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-cache</td>
 * <td>cac&nbsp;cache&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-cache-csf</td>
 * <td>csf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-cactvs-binary</td>
 * <td>cbin&nbsp;cascii&nbsp;ctab&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-cdx</td>
 * <td>cdx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-cerius</td>
 * <td>cer&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-chem3d</td>
 * <td>c3d&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-chemdraw</td>
 * <td>chm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-cif</td>
 * <td>cif&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-cmdf</td>
 * <td>cmdf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-cml</td>
 * <td>cml&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-compass</td>
 * <td>cpa&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-crossfire</td>
 * <td>bsd&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-csml</td>
 * <td>csml&nbsp;csm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-ctx</td>
 * <td>ctx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-cxf</td>
 * <td>cxf&nbsp;cef&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-embl-dl-nucleotide</td>
 * <td>emb&nbsp;embl&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-galactic-spc</td>
 * <td>spc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-gamess-input</td>
 * <td>inp&nbsp;gam&nbsp;gamin&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-gaussian-checkpoint</td>
 * <td>fch&nbsp;fchk&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-gaussian-cube</td>
 * <td>cub&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-gaussian-input</td>
 * <td>gau&nbsp;gjc&nbsp;gjf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-gaussian-log</td>
 * <td>gal&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-gcg8-sequence</td>
 * <td>gcg&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-genbank</td>
 * <td>gen&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-hin</td>
 * <td>hin&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-isostar</td>
 * <td>istr&nbsp;ist&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-jcamp-dx</td>
 * <td>jdx&nbsp;dx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-kinemage</td>
 * <td>kin&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-macmolecule</td>
 * <td>mcm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-macromodel-input</td>
 * <td>mmd&nbsp;mmod&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-mdl-molfile</td>
 * <td>mol&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-mdl-rdfile</td>
 * <td>rd&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-mdl-rxnfile</td>
 * <td>rxn&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-mdl-sdfile</td>
 * <td>sd&nbsp;sdf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-mdl-tgf</td>
 * <td>tgf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-mmcif</td>
 * <td>mcif&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-mol2</td>
 * <td>mol2&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-molconn-Z</td>
 * <td>b&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-mopac-graph</td>
 * <td>gpt&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-mopac-input</td>
 * <td>mop&nbsp;mopcrt&nbsp;mpc&nbsp;dat&nbsp;zmt&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-mopac-out</td>
 * <td>moo&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-mopac-vib</td>
 * <td>mvb&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-ncbi-asn1</td>
 * <td>asn&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-ncbi-asn1-ascii</td>
 * <td>prt&nbsp;ent&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-ncbi-asn1-binary</td>
 * <td>val&nbsp;aso&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-ncbi-asn1-spec</td>
 * <td>asn&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-pdb</td>
 * <td>pdb&nbsp;ent&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-rosdal</td>
 * <td>ros&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-swissprot</td>
 * <td>sw&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-vamas-iso14976</td>
 * <td>vms&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-vmd</td>
 * <td>vmd&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-xtel</td>
 * <td>xtel&nbsp;</td>
 * </tr>
 * <tr>
 * <td>chemical/x-xyz</td>
 * <td>xyz&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/gif</td>
 * <td>gif&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/ief</td>
 * <td>ief&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/jpeg</td>
 * <td>jpeg&nbsp;jpg&nbsp;jpe&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/pcx</td>
 * <td>pcx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/png</td>
 * <td>png&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/svg+xml</td>
 * <td>svg&nbsp;svgz&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/tiff</td>
 * <td>tiff&nbsp;tif&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/vnd.djvu</td>
 * <td>djvu&nbsp;djv&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/vnd.wap.wbmp</td>
 * <td>wbmp&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-cmu-raster</td>
 * <td>ras&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-coreldraw</td>
 * <td>cdr&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-coreldrawpattern</td>
 * <td>pat&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-coreldrawtemplate</td>
 * <td>cdt&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-corelphotopaint</td>
 * <td>cpt&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-icon</td>
 * <td>ico&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-jg</td>
 * <td>art&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-jng</td>
 * <td>jng&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-ms-bmp</td>
 * <td>bmp&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-photoshop</td>
 * <td>psd&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-portable-anymap</td>
 * <td>pnm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-portable-bitmap</td>
 * <td>pbm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-portable-graymap</td>
 * <td>pgm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-portable-pixmap</td>
 * <td>ppm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-rgb</td>
 * <td>rgb&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-xbitmap</td>
 * <td>xbm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-xpixmap</td>
 * <td>xpm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>image/x-xwindowdump</td>
 * <td>xwd&nbsp;</td>
 * </tr>
 * <tr>
 * <td>message/rfc822</td>
 * <td>eml&nbsp;</td>
 * </tr>
 * <tr>
 * <td>model/iges</td>
 * <td>igs&nbsp;iges&nbsp;</td>
 * </tr>
 * <tr>
 * <td>model/mesh</td>
 * <td>msh&nbsp;mesh&nbsp;silo&nbsp;</td>
 * </tr>
 * <tr>
 * <td>model/vrml</td>
 * <td>wrl&nbsp;vrml&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/calendar</td>
 * <td>ics&nbsp;icz&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/css</td>
 * <td>css&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/csv</td>
 * <td>csv&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/h323</td>
 * <td>323&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/html</td>
 * <td>html&nbsp;htm&nbsp;shtml&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/iuls</td>
 * <td>uls&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/mathml</td>
 * <td>mml&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/plain</td>
 * <td>asc&nbsp;txt&nbsp;text&nbsp;pot&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/richtext</td>
 * <td>rtx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/scriptlet</td>
 * <td>sct&nbsp;wsc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/texmacs</td>
 * <td>tm&nbsp;ts&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/tab-separated-values</td>
 * <td>tsv&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/vnd.sun.j2me.app-descriptor</td>
 * <td>jad&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/vnd.wap.wml</td>
 * <td>wml&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/vnd.wap.wmlscript</td>
 * <td>wmls&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-bibtex</td>
 * <td>bib&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-boo</td>
 * <td>boo&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-c++hdr</td>
 * <td>h++&nbsp;hpp&nbsp;hxx&nbsp;hh&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-c++src</td>
 * <td>c++&nbsp;cpp&nbsp;cxx&nbsp;cc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-chdr</td>
 * <td>h&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-component</td>
 * <td>htc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-csh</td>
 * <td>csh&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-csrc</td>
 * <td>c&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-dsrc</td>
 * <td>d&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-diff</td>
 * <td>diff&nbsp;patch&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-haskell</td>
 * <td>hs&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-java</td>
 * <td>java&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-literate-haskell</td>
 * <td>lhs&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-moc</td>
 * <td>moc&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-pascal</td>
 * <td>p&nbsp;pas&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-pcs-gcd</td>
 * <td>gcd&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-perl</td>
 * <td>pl&nbsp;pm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-python</td>
 * <td>py&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-setext</td>
 * <td>etx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-sh</td>
 * <td>sh&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-tcl</td>
 * <td>tcl&nbsp;tk&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-tex</td>
 * <td>tex&nbsp;ltx&nbsp;sty&nbsp;cls&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-vcalendar</td>
 * <td>vcs&nbsp;</td>
 * </tr>
 * <tr>
 * <td>text/x-vcard</td>
 * <td>vcf&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/3gpp</td>
 * <td>3gp&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/dl</td>
 * <td>dl&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/dv</td>
 * <td>dif&nbsp;dv&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/fli</td>
 * <td>fli&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/gl</td>
 * <td>gl&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/mpeg</td>
 * <td>mpeg&nbsp;mpg&nbsp;mpe&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/mp4</td>
 * <td>mp4&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/ogg</td>
 * <td>ogv&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/quicktime</td>
 * <td>qt&nbsp;mov&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/vnd.mpegurl</td>
 * <td>mxu&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/x-la-asf</td>
 * <td>lsf&nbsp;lsx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/x-mng</td>
 * <td>mng&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/x-ms-asf</td>
 * <td>asf&nbsp;asx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/x-ms-wm</td>
 * <td>wm&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/x-ms-wmv</td>
 * <td>wmv&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/x-ms-wmx</td>
 * <td>wmx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/x-ms-wvx</td>
 * <td>wvx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/x-msvideo</td>
 * <td>avi&nbsp;</td>
 * </tr>
 * <tr>
 * <td>video/x-sgi-movie</td>
 * <td>movie&nbsp;</td>
 * </tr>
 * <tr>
 * <td>x-conference/x-cooltalk</td>
 * <td>ice&nbsp;</td>
 * </tr>
 * <tr>
 * <td>x-epoc/x-sisx-app</td>
 * <td>sisx&nbsp;</td>
 * </tr>
 * <tr>
 * <td>x-world/x-vrml</td>
 * <td>vrm&nbsp;vrml&nbsp;wrl&nbsp;</td>
 * </tr>
 * </table>
 */
public final class MimeType {
	/** 拡張子からMimeTypeへの変換Map */
	private static final Map<String, String> EXT_TO_MIME = new HashMap<String, String>();

	/** MimeTypeから拡張子への変換Map */
	private static final Map<String, String[]> MIME_TO_EXT = new HashMap<String, String[]>();

	static {
		setMap("application/andrew-inset", new String[] { "ez" });
		setMap("application/atom", new String[] { "atom" });
		setMap("application/atomcat+xml", new String[] { "atomcat" });
		setMap("application/atomserv+xml", new String[] { "atomsrv" });
		setMap("application/cap", new String[] { "cap", "pcap" });
		setMap("application/cu-seeme", new String[] { "cu" });
		setMap("application/dsptype", new String[] { "tsp" });
		setMap("application/futuresplash", new String[] { "spl" });
		setMap("application/hta", new String[] { "hta" });
		setMap("application/java-archive", new String[] { "jar" });
		setMap("application/java-serialized-object", new String[] { "ser" });
		setMap("application/java-vm", new String[] { "class" });
		setMap("application/mac-binhex40", new String[] { "hqx" });
		setMap("application/mac-compactpro", new String[] { "cpt" });
		setMap("application/mathematica", new String[] { "nb" });
		setMap("application/msaccess", new String[] { "mdb" });
		setMap("application/msword", new String[] { "doc", "dot" });
		setMap("application/octet-stream", new String[] { "bin" });
		setMap("application/oda", new String[] { "oda" });
		setMap("application/ogg", new String[] { "ogg", "ogx" });
		setMap("application/pdf", new String[] { "pdf" });
		setMap("application/pgp-keys", new String[] { "key" });
		setMap("application/pgp-signature", new String[] { "pgp" });
		setMap("application/pics-rules", new String[] { "prf" });
		setMap("application/postscript", new String[] { "ps", "ai", "eps" });
		setMap("application/rar", new String[] { "rar" });
		setMap("application/rdf+xml", new String[] { "rdf" });
		setMap("application/rss+xml", new String[] { "rss" });
		setMap("application/rtf", new String[] { "rtf" });
		setMap("application/smil", new String[] { "smi", "smil" });
		setMap("application/wordperfect", new String[] { "wpd" });
		setMap("application/wordperfect5.1", new String[] { "wp5" });
		setMap("application/xhtml+xml", new String[] { "xhtml", "xht" });
		setMap("application/xml", new String[] { "xml", "xsl" });
		setMap("application/zip", new String[] { "zip" });
		setMap("application/vnd.cinderella", new String[] { "cdy" });
		setMap("application/vnd.google-earth.kml+xml", new String[] { "kml" });
		setMap("application/vnd.google-earth.kmz", new String[] { "kmz" });
		setMap("application/vnd.mozilla.xul+xml", new String[] { "xul" });
		setMap("application/vnd.ms-excel", new String[] { "xls", "xlb", "xlt" });
		setMap("application/vnd.ms-pki.seccat", new String[] { "cat" });
		setMap("application/vnd.ms-pki.stl", new String[] { "stl" });
		setMap("application/vnd.ms-powerpoint", new String[] { "ppt", "pps" });
		setMap("application/vnd.oasis.opendocument.chart",
				new String[] { "odc" });
		setMap("application/vnd.oasis.opendocument.database",
				new String[] { "odb" });
		setMap("application/vnd.oasis.opendocument.formula",
				new String[] { "odf" });
		setMap("application/vnd.oasis.opendocument.graphics",
				new String[] { "odg" });
		setMap("application/vnd.oasis.opendocument.graphics-template",
				new String[] { "otg" });
		setMap("application/vnd.oasis.opendocument.image",
				new String[] { "odi" });
		setMap("application/vnd.oasis.opendocument.presentation",
				new String[] { "odp" });
		setMap("application/vnd.oasis.opendocument.presentation-template",
				new String[] { "otp" });
		setMap("application/vnd.oasis.opendocument.spreadsheet",
				new String[] { "ods" });
		setMap("application/vnd.oasis.opendocument.spreadsheet-template",
				new String[] { "ots" });
		setMap("application/vnd.oasis.opendocument.text",
				new String[] { "odt" });
		setMap("application/vnd.oasis.opendocument.text-master",
				new String[] { "odm" });
		setMap("application/vnd.oasis.opendocument.text-template",
				new String[] { "ott" });
		setMap("application/vnd.oasis.opendocument.text-web",
				new String[] { "oth" });
		setMap("application/vnd.rim.cod", new String[] { "cod" });
		setMap("application/vnd.smaf", new String[] { "mmf" });
		setMap("application/vnd.stardivision.calc", new String[] { "sdc" });
		setMap("application/vnd.stardivision.chart", new String[] { "sds" });
		setMap("application/vnd.stardivision.draw", new String[] { "sda" });
		setMap("application/vnd.stardivision.impress", new String[] { "sdd" });
		setMap("application/vnd.stardivision.math", new String[] { "sdf" });
		setMap("application/vnd.stardivision.writer", new String[] { "sdw" });
		setMap("application/vnd.stardivision.writer-global",
				new String[] { "sgl" });
		setMap("application/vnd.sun.xml.calc", new String[] { "sxc" });
		setMap("application/vnd.sun.xml.calc.template", new String[] { "stc" });
		setMap("application/vnd.sun.xml.draw", new String[] { "sxd" });
		setMap("application/vnd.sun.xml.draw.template", new String[] { "std" });
		setMap("application/vnd.sun.xml.impress", new String[] { "sxi" });
		setMap("application/vnd.sun.xml.impress.template",
				new String[] { "sti" });
		setMap("application/vnd.sun.xml.math", new String[] { "sxm" });
		setMap("application/vnd.sun.xml.writer", new String[] { "sxw" });
		setMap("application/vnd.sun.xml.writer.global", new String[] { "sxg" });
		setMap("application/vnd.sun.xml.writer.template",
				new String[] { "stw" });
		setMap("application/vnd.symbian.install", new String[] { "sis" });
		setMap("application/vnd.visio", new String[] { "vsd" });
		setMap("application/vnd.wap.wbxml", new String[] { "wbxml" });
		setMap("application/vnd.wap.wmlc", new String[] { "wmlc" });
		setMap("application/vnd.wap.wmlscriptc", new String[] { "wmlsc" });
		setMap("application/x-123", new String[] { "wk" });
		setMap("application/x-7z-compressed", new String[] { "7z" });
		setMap("application/x-abiword", new String[] { "abw" });
		setMap("application/x-apple-diskimage", new String[] { "dmg" });
		setMap("application/x-bcpio", new String[] { "bcpio" });
		setMap("application/x-bittorrent", new String[] { "torrent" });
		setMap("application/x-cab", new String[] { "cab" });
		setMap("application/x-cbr", new String[] { "cbr" });
		setMap("application/x-cbz", new String[] { "cbz" });
		setMap("application/x-cdf", new String[] { "cdf" });
		setMap("application/x-cdlink", new String[] { "vcd" });
		setMap("application/x-chess-pgn", new String[] { "pgn" });
		setMap("application/x-cpio", new String[] { "cpio" });
		setMap("application/x-csh", new String[] { "csh" });
		setMap("application/x-debian-package", new String[] { "deb", "udeb" });
		setMap("application/x-director", new String[] { "dcr", "dir", "dxr" });
		setMap("application/x-dms", new String[] { "dms" });
		setMap("application/x-doom", new String[] { "wad" });
		setMap("application/x-dvi", new String[] { "dvi" });
		setMap("application/x-httpd-eruby", new String[] { "rhtml" });
		setMap("application/x-flac", new String[] { "flac" });
		setMap("application/x-font", new String[] { "pfa", "pfb", "gsf", "pcf",
				"pcf.Z" });
		setMap("application/x-freemind", new String[] { "mm" });
		setMap("application/x-futuresplash", new String[] { "spl" });
		setMap("application/x-gnumeric", new String[] { "gnumeric" });
		setMap("application/x-go-sgf", new String[] { "sgf" });
		setMap("application/x-graphing-calculator", new String[] { "gcf" });
		setMap("application/x-gtar", new String[] { "gtar", "tgz", "taz" });
		setMap("application/x-hdf", new String[] { "hdf" });
		setMap("application/x-httpd-php",
				new String[] { "phtml", "pht", "php" });
		setMap("application/x-httpd-php-source", new String[] { "phps" });
		setMap("application/x-httpd-php3", new String[] { "php3" });
		setMap("application/x-httpd-php3-preprocessed",
				new String[] { "php3p" });
		setMap("application/x-httpd-php4", new String[] { "php4" });
		setMap("application/x-ica", new String[] { "ica" });
		setMap("application/x-internet-signup", new String[] { "ins", "isp" });
		setMap("application/x-iphone", new String[] { "iii" });
		setMap("application/x-iso9660-image", new String[] { "iso" });
		setMap("application/x-java-jnlp-file", new String[] { "jnlp" });
		setMap("application/x-javascript", new String[] { "js" });
		setMap("application/x-jmol", new String[] { "jmz" });
		setMap("application/x-kchart", new String[] { "chrt" });
		setMap("application/x-killustrator", new String[] { "kil" });
		setMap("application/x-koan",
				new String[] { "skp", "skd", "skt", "skm" });
		setMap("application/x-kpresenter", new String[] { "kpr", "kpt" });
		setMap("application/x-kspread", new String[] { "ksp" });
		setMap("application/x-kword", new String[] { "kwd", "kwt" });
		setMap("application/x-latex", new String[] { "latex" });
		setMap("application/x-lha", new String[] { "lha" });
		setMap("application/x-lyx", new String[] { "lyx" });
		setMap("application/x-lzh", new String[] { "lzh" });
		setMap("application/x-lzx", new String[] { "lzx" });
		setMap("application/x-maker", new String[] { "frm", "maker", "frame",
				"fm", "fb", "book", "fbdoc" });
		setMap("application/x-mif", new String[] { "mif" });
		setMap("application/x-ms-wmd", new String[] { "wmd" });
		setMap("application/x-ms-wmz", new String[] { "wmz" });
		setMap("application/x-msdos-program", new String[] { "com", "exe",
				"bat", "dll" });
		setMap("application/x-msi", new String[] { "msi" });
		setMap("application/x-netcdf", new String[] { "nc" });
		setMap("application/x-ns-proxy-autoconfig", new String[] { "pac" });
		setMap("application/x-nwc", new String[] { "nwc" });
		setMap("application/x-object", new String[] { "o" });
		setMap("application/x-oz-application", new String[] { "oza" });
		setMap("application/x-pkcs7-certreqresp", new String[] { "p7r" });
		setMap("application/x-pkcs7-crl", new String[] { "crl" });
		setMap("application/x-python-code", new String[] { "pyc", "pyo" });
		setMap("application/x-quicktimeplayer", new String[] { "qtl" });
		setMap("application/x-redhat-package-manager", new String[] { "rpm" });
		setMap("application/x-sh", new String[] { "sh" });
		setMap("application/x-shar", new String[] { "shar" });
		setMap("application/x-shockwave-flash", new String[] { "swf", "swfl" });
		setMap("application/x-stuffit", new String[] { "sit", "sitx" });
		setMap("application/x-sv4cpio", new String[] { "sv4cpio" });
		setMap("application/x-sv4crc", new String[] { "sv4crc" });
		setMap("application/x-tar", new String[] { "tar" });
		setMap("application/x-tcl", new String[] { "tcl" });
		setMap("application/x-tex-gf", new String[] { "gf" });
		setMap("application/x-tex-pk", new String[] { "pk" });
		setMap("application/x-texinfo", new String[] { "texinfo", "texi" });
		setMap("application/x-trash", new String[] { "~", "%", "bak", "old",
				"sik" });
		setMap("application/x-troff", new String[] { "t", "tr", "roff" });
		setMap("application/x-troff-man", new String[] { "man" });
		setMap("application/x-troff-me", new String[] { "me" });
		setMap("application/x-troff-ms", new String[] { "ms" });
		setMap("application/x-ustar", new String[] { "ustar" });
		setMap("application/x-wais-source", new String[] { "src" });
		setMap("application/x-wingz", new String[] { "wz" });
		setMap("application/x-x509-ca-cert", new String[] { "crt" });
		setMap("application/x-xcf", new String[] { "xcf" });
		setMap("application/x-xfig", new String[] { "fig" });
		setMap("application/x-xpinstall", new String[] { "xpi" });
		setMap("audio/basic", new String[] { "au", "snd" });
		setMap("audio/midi", new String[] { "mid", "midi", "kar" });
		setMap("audio/mpeg", new String[] { "mpga", "mpega", "mp2", "mp3",
				"m4a" });
		setMap("audio/mpegurl", new String[] { "m3u" });
		setMap("audio/ogg", new String[] { "oga", "spx" });
		setMap("audio/prs.sid", new String[] { "sid" });
		setMap("audio/x-aiff", new String[] { "aif", "aiff", "aifc" });
		setMap("audio/x-gsm", new String[] { "gsm" });
		setMap("audio/x-mpegurl", new String[] { "m3u" });
		setMap("audio/x-ms-wma", new String[] { "wma" });
		setMap("audio/x-ms-wax", new String[] { "wax" });
		setMap("audio/x-pn-realaudio", new String[] { "ra", "rm", "ram" });
		setMap("audio/x-realaudio", new String[] { "ra" });
		setMap("audio/x-scpls", new String[] { "pls" });
		setMap("audio/x-sd2", new String[] { "sd2" });
		setMap("audio/x-wav", new String[] { "wav" });
		setMap("chemical/x-alchemy", new String[] { "alc" });
		setMap("chemical/x-cache", new String[] { "cac", "cache" });
		setMap("chemical/x-cache-csf", new String[] { "csf" });
		setMap("chemical/x-cactvs-binary", new String[] { "cbin", "cascii",
				"ctab" });
		setMap("chemical/x-cdx", new String[] { "cdx" });
		setMap("chemical/x-cerius", new String[] { "cer" });
		setMap("chemical/x-chem3d", new String[] { "c3d" });
		setMap("chemical/x-chemdraw", new String[] { "chm" });
		setMap("chemical/x-cif", new String[] { "cif" });
		setMap("chemical/x-cmdf", new String[] { "cmdf" });
		setMap("chemical/x-cml", new String[] { "cml" });
		setMap("chemical/x-compass", new String[] { "cpa" });
		setMap("chemical/x-crossfire", new String[] { "bsd" });
		setMap("chemical/x-csml", new String[] { "csml", "csm" });
		setMap("chemical/x-ctx", new String[] { "ctx" });
		setMap("chemical/x-cxf", new String[] { "cxf", "cef" });
		setMap("chemical/x-embl-dl-nucleotide", new String[] { "emb", "embl" });
		setMap("chemical/x-galactic-spc", new String[] { "spc" });
		setMap("chemical/x-gamess-input",
				new String[] { "inp", "gam", "gamin" });
		setMap("chemical/x-gaussian-checkpoint", new String[] { "fch", "fchk" });
		setMap("chemical/x-gaussian-cube", new String[] { "cub" });
		setMap("chemical/x-gaussian-input",
				new String[] { "gau", "gjc", "gjf" });
		setMap("chemical/x-gaussian-log", new String[] { "gal" });
		setMap("chemical/x-gcg8-sequence", new String[] { "gcg" });
		setMap("chemical/x-genbank", new String[] { "gen" });
		setMap("chemical/x-hin", new String[] { "hin" });
		setMap("chemical/x-isostar", new String[] { "istr", "ist" });
		setMap("chemical/x-jcamp-dx", new String[] { "jdx", "dx" });
		setMap("chemical/x-kinemage", new String[] { "kin" });
		setMap("chemical/x-macmolecule", new String[] { "mcm" });
		setMap("chemical/x-macromodel-input", new String[] { "mmd", "mmod" });
		setMap("chemical/x-mdl-molfile", new String[] { "mol" });
		setMap("chemical/x-mdl-rdfile", new String[] { "rd" });
		setMap("chemical/x-mdl-rxnfile", new String[] { "rxn" });
		setMap("chemical/x-mdl-sdfile", new String[] { "sd", "sdf" });
		setMap("chemical/x-mdl-tgf", new String[] { "tgf" });
		setMap("chemical/x-mmcif", new String[] { "mcif" });
		setMap("chemical/x-mol2", new String[] { "mol2" });
		setMap("chemical/x-molconn-Z", new String[] { "b" });
		setMap("chemical/x-mopac-graph", new String[] { "gpt" });
		setMap("chemical/x-mopac-input", new String[] { "mop", "mopcrt", "mpc",
				"dat", "zmt" });
		setMap("chemical/x-mopac-out", new String[] { "moo" });
		setMap("chemical/x-mopac-vib", new String[] { "mvb" });
		setMap("chemical/x-ncbi-asn1", new String[] { "asn" });
		setMap("chemical/x-ncbi-asn1-ascii", new String[] { "prt", "ent" });
		setMap("chemical/x-ncbi-asn1-binary", new String[] { "val", "aso" });
		setMap("chemical/x-ncbi-asn1-spec", new String[] { "asn" });
		setMap("chemical/x-pdb", new String[] { "pdb", "ent" });
		setMap("chemical/x-rosdal", new String[] { "ros" });
		setMap("chemical/x-swissprot", new String[] { "sw" });
		setMap("chemical/x-vamas-iso14976", new String[] { "vms" });
		setMap("chemical/x-vmd", new String[] { "vmd" });
		setMap("chemical/x-xtel", new String[] { "xtel" });
		setMap("chemical/x-xyz", new String[] { "xyz" });
		setMap("image/gif", new String[] { "gif" });
		setMap("image/ief", new String[] { "ief" });
		setMap("image/jpeg", new String[] { "jpeg", "jpg", "jpe" });
		setMap("image/pcx", new String[] { "pcx" });
		setMap("image/png", new String[] { "png" });
		setMap("image/svg+xml", new String[] { "svg", "svgz" });
		setMap("image/tiff", new String[] { "tiff", "tif" });
		setMap("image/vnd.djvu", new String[] { "djvu", "djv" });
		setMap("image/vnd.wap.wbmp", new String[] { "wbmp" });
		setMap("image/x-cmu-raster", new String[] { "ras" });
		setMap("image/x-coreldraw", new String[] { "cdr" });
		setMap("image/x-coreldrawpattern", new String[] { "pat" });
		setMap("image/x-coreldrawtemplate", new String[] { "cdt" });
		setMap("image/x-corelphotopaint", new String[] { "cpt" });
		setMap("image/x-icon", new String[] { "ico" });
		setMap("image/x-jg", new String[] { "art" });
		setMap("image/x-jng", new String[] { "jng" });
		setMap("image/x-ms-bmp", new String[] { "bmp" });
		setMap("image/x-photoshop", new String[] { "psd" });
		setMap("image/x-portable-anymap", new String[] { "pnm" });
		setMap("image/x-portable-bitmap", new String[] { "pbm" });
		setMap("image/x-portable-graymap", new String[] { "pgm" });
		setMap("image/x-portable-pixmap", new String[] { "ppm" });
		setMap("image/x-rgb", new String[] { "rgb" });
		setMap("image/x-xbitmap", new String[] { "xbm" });
		setMap("image/x-xpixmap", new String[] { "xpm" });
		setMap("image/x-xwindowdump", new String[] { "xwd" });
		setMap("message/rfc822", new String[] { "eml" });
		setMap("model/iges", new String[] { "igs", "iges" });
		setMap("model/mesh", new String[] { "msh", "mesh", "silo" });
		setMap("model/vrml", new String[] { "wrl", "vrml" });
		setMap("text/calendar", new String[] { "ics", "icz" });
		setMap("text/css", new String[] { "css" });
		setMap("text/csv", new String[] { "csv" });
		setMap("text/h323", new String[] { "323" });
		setMap("text/html", new String[] { "html", "htm", "shtml" });
		setMap("text/iuls", new String[] { "uls" });
		setMap("text/mathml", new String[] { "mml" });
		setMap("text/plain", new String[] { "asc", "txt", "text/pot" });
		setMap("text/richtext", new String[] { "rtx" });
		setMap("text/scriptlet", new String[] { "sct", "wsc" });
		setMap("text/texmacs", new String[] { "tm", "ts" });
		setMap("text/tab-separated-values", new String[] { "tsv" });
		setMap("text/vnd.sun.j2me.app-descriptor", new String[] { "jad" });
		setMap("text/vnd.wap.wml", new String[] { "wml" });
		setMap("text/vnd.wap.wmlscript", new String[] { "wmls" });
		setMap("text/x-bibtex", new String[] { "bib" });
		setMap("text/x-boo", new String[] { "boo" });
		setMap("text/x-c++hdr", new String[] { "h++", "hpp", "hxx", "hh" });
		setMap("text/x-c++src", new String[] { "c++", "cpp", "cxx", "cc" });
		setMap("text/x-chdr", new String[] { "h" });
		setMap("text/x-component", new String[] { "htc" });
		setMap("text/x-csh", new String[] { "csh" });
		setMap("text/x-csrc", new String[] { "c" });
		setMap("text/x-dsrc", new String[] { "d" });
		setMap("text/x-diff", new String[] { "diff", "patch" });
		setMap("text/x-haskell", new String[] { "hs" });
		setMap("text/x-java", new String[] { "java" });
		setMap("text/x-literate-haskell", new String[] { "lhs" });
		setMap("text/x-moc", new String[] { "moc" });
		setMap("text/x-pascal", new String[] { "p", "pas" });
		setMap("text/x-pcs-gcd", new String[] { "gcd" });
		setMap("text/x-perl", new String[] { "pl", "pm" });
		setMap("text/x-python", new String[] { "py" });
		setMap("text/x-setext", new String[] { "etx" });
		setMap("text/x-sh", new String[] { "sh" });
		setMap("text/x-tcl", new String[] { "tcl", "tk" });
		setMap("text/x-tex", new String[] { "tex", "ltx", "sty", "cls" });
		setMap("text/x-vcalendar", new String[] { "vcs" });
		setMap("text/x-vcard", new String[] { "vcf" });
		setMap("video/3gpp", new String[] { "3gp" });
		setMap("video/dl", new String[] { "dl" });
		setMap("video/dv", new String[] { "dif", "dv" });
		setMap("video/fli", new String[] { "fli" });
		setMap("video/gl", new String[] { "gl" });
		setMap("video/mpeg", new String[] { "mpeg", "mpg", "mpe" });
		setMap("video/mp4", new String[] { "mp4" });
		setMap("video/ogg", new String[] { "ogv" });
		setMap("video/quicktime", new String[] { "qt", "mov" });
		setMap("video/vnd.mpegurl", new String[] { "mxu" });
		setMap("video/x-la-asf", new String[] { "lsf", "lsx" });
		setMap("video/x-mng", new String[] { "mng" });
		setMap("video/x-ms-asf", new String[] { "asf", "asx" });
		setMap("video/x-ms-wm", new String[] { "wm" });
		setMap("video/x-ms-wmv", new String[] { "wmv" });
		setMap("video/x-ms-wmx", new String[] { "wmx" });
		setMap("video/x-ms-wvx", new String[] { "wvx" });
		setMap("video/x-msvideo", new String[] { "avi" });
		setMap("video/x-sgi-movie", new String[] { "movie" });
		setMap("x-conference/x-cooltalk", new String[] { "ice" });
		setMap("x-epoc/x-sisx-app", new String[] { "sisx" });
		setMap("x-world/x-vrml", new String[] { "vrm", "vrml", "wrl" });
	}

	/**
	 * インスタンス化されないようにするための内部コンストラクタ.
	 */
	private MimeType() {
	}

	/**
	 * 変換Mapにセットします.
	 *
	 * @param mimeType
	 *            MimeType
	 * @param extensions
	 *            拡張子群
	 */
	private static void setMap(String mimeType, String[] extensions) {
		// MimeTypeから拡張子への変換Mapにセット
		MIME_TO_EXT.put(mimeType, extensions);
		// 拡張子からMimeTypeへの変換Mapにセット
		for (String extension : extensions) {
			EXT_TO_MIME.put(extension, mimeType);
		}
	}

	/**
	 * 拡張子から判定してMimeTypeを取得.
	 *
	 * @param filename
	 *            ファイル名、もしくは拡張子そのもの
	 * @return MimeType.<br>
	 *         該当するMimeTypeが無ければnullを返します.
	 */
	public static String getMimeType(String filename) {
		// 末尾からドットを探す
		int dotIndex = filename.lastIndexOf(".");
		String extension;
		if (dotIndex < 0) {
			// ドットがなければ全体が拡張子と判断
			extension = filename;
		} else {
			// ドットの次の文字から後ろを拡張子とする
			extension = filename.substring(dotIndex + 1);
		}
		// 小文字変換
		extension = extension.toLowerCase();
		// 検索
		String mimeType = EXT_TO_MIME.get(extension);
		if (Validator.isEmpty(mimeType)) {
			mimeType = null;
		}
		return mimeType;
	}

	/**
	 * MimeTypeから判定して拡張子を取得.
	 *
	 * @param mimeType
	 *            MimeType
	 * @return 拡張子群.<br>
	 *         該当する拡張子が無ければnullを返します.
	 */
	public static String[] getExtension(String mimeType) {
		// 検索
		String[] extensions = MIME_TO_EXT.get(mimeType);
		if (extensions == null || extensions.length == 0) {
			return null;
		}
		return extensions;
	}
}
