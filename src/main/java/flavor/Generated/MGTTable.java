/* @flavorc
 *
 * MGTTable.java
 * 
 * This file was automatically generated by flavorc
 * from the source file:
 *     '../FormalDefs/MGT_section.fl'
 *
 * For information on flavorc, visit the Flavor Web site at:
 *     http://flavor.sourceforge.net
 *
 * -- Do not edit by hand --
 *
 */

package flavor.Generated;
import flavor.*;
import java.io.*;

public class MGTTable {
    int table_type;
    int reserved1;
    int table_type_PID;
    int reserved2;
    int table_type_version_number;
    int number_bytes;
    int reserved3;
    int table_type_descriptors_length;
    int remain_desc_bytes;
    Descriptor descriptor;

    public int get(IBitstream _F_bs) throws IOException {
        int _F_ret = 0;
        MapResult _F_mr;
        int _F_parse = 0;
        Util.trace(_F_bs.getpos(), 0, (int)0, "begin MGTTable");
        _F_parse = 16;
        table_type = _F_bs.getbits(_F_parse);
        Util.trace(_F_bs.getpos()-_F_parse, _F_parse, (int)table_type, "table_type" +  " (" + table_type + ")");
        _F_parse = 3;
        reserved1 = _F_bs.getbits(_F_parse);
        if (reserved1 != 7) {
            Util.flerror("Const value mismatch for 'reserved1'");
            _F_ret++;
            Util.trace(_F_bs.getpos()-_F_parse, _F_parse, (int)reserved1, "reserved1" +  " (" + reserved1 + ") [ERROR]");
        }
        else {
            Util.trace(_F_bs.getpos()-_F_parse, _F_parse, (int)reserved1, "reserved1" +  " (" + reserved1 + ")");
        }
        _F_parse = 13;
        table_type_PID = _F_bs.getbits(_F_parse);
        Util.trace(_F_bs.getpos()-_F_parse, _F_parse, (int)table_type_PID, "table_type_PID" +  " (" + table_type_PID + ")");
        _F_parse = 3;
        reserved2 = _F_bs.getbits(_F_parse);
        if (reserved2 != 7) {
            Util.flerror("Const value mismatch for 'reserved2'");
            _F_ret++;
            Util.trace(_F_bs.getpos()-_F_parse, _F_parse, (int)reserved2, "reserved2" +  " (" + reserved2 + ") [ERROR]");
        }
        else {
            Util.trace(_F_bs.getpos()-_F_parse, _F_parse, (int)reserved2, "reserved2" +  " (" + reserved2 + ")");
        }
        _F_parse = 5;
        table_type_version_number = _F_bs.getbits(_F_parse);
        Util.trace(_F_bs.getpos()-_F_parse, _F_parse, (int)table_type_version_number, "table_type_version_number" +  " (" + table_type_version_number + ")");
        _F_parse = 32;
        number_bytes = _F_bs.getbits(_F_parse);
        Util.trace(_F_bs.getpos()-_F_parse, _F_parse, (int)number_bytes, "number_bytes" +  " (" + number_bytes + ")");
        _F_parse = 4;
        reserved3 = _F_bs.getbits(_F_parse);
        if (reserved3 != 15) {
            Util.flerror("Const value mismatch for 'reserved3'");
            _F_ret++;
            Util.trace(_F_bs.getpos()-_F_parse, _F_parse, (int)reserved3, "reserved3" +  " (" + reserved3 + ") [ERROR]");
        }
        else {
            Util.trace(_F_bs.getpos()-_F_parse, _F_parse, (int)reserved3, "reserved3" +  " (" + reserved3 + ")");
        }
        _F_parse = 12;
        table_type_descriptors_length = _F_bs.getbits(_F_parse);
        Util.trace(_F_bs.getpos()-_F_parse, _F_parse, (int)table_type_descriptors_length, "table_type_descriptors_length" +  " (" + table_type_descriptors_length + ")");
        remain_desc_bytes = table_type_descriptors_length;
        while (remain_desc_bytes>0) {
            Util.trace(_F_bs.getpos(), 0, (int)0, "processing Descriptor descriptor");
            descriptor = new Descriptor();
            _F_ret += descriptor.get(_F_bs);
            remain_desc_bytes-=(descriptor._F_lengthof/8);
            if (1==0) break;
        }
        Util.trace(_F_bs.getpos(), 0, (int)0, "end MGTTable");
        return _F_ret;
    }

    public int put(IBitstream _F_bs) throws IOException {
        int _F_ret = 0;
        MapResult _F_mr;
        int _F_parse = 0;
        _F_bs.putbits(table_type, 16);
        _F_parse = 3;
        reserved1 = 7;
        _F_bs.putbits(reserved1, _F_parse);
        _F_bs.putbits(table_type_PID, 13);
        _F_parse = 3;
        reserved2 = 7;
        _F_bs.putbits(reserved2, _F_parse);
        _F_bs.putbits(table_type_version_number, 5);
        _F_bs.putbits(number_bytes, 32);
        _F_parse = 4;
        reserved3 = 15;
        _F_bs.putbits(reserved3, _F_parse);
        _F_bs.putbits(table_type_descriptors_length, 12);
        remain_desc_bytes = table_type_descriptors_length;
        while (remain_desc_bytes>0) {
            _F_ret += descriptor.put(_F_bs);
            remain_desc_bytes-=(descriptor._F_lengthof/8);
            if (1==0) break;
        }
        return _F_ret;
    }

    public int putxml(IBitstream _F_bs, boolean bAttr) throws IOException {
        int _F_ret = 0;
        MapResult _F_mr;
        int _F_parse = 0;
        _F_parse = 16;
        table_type = _F_bs.getbits(_F_parse);
        if (bAttr) {
            XML.WriteXmlElement("<table_type type=\"flUInt\" bitLen=\"" + _F_parse + "\">" + table_type + "</table_type>");
        }
        else {
            XML.WriteXmlElement("<table_type bitLen=\"" + _F_parse + "\">" + table_type + "</table_type>");
        }
        _F_parse = 3;
        reserved1 = _F_bs.getbits(_F_parse);
        if (reserved1 != 7) {
            Util.flerror("Const value mismatch for 'reserved1'");
            _F_ret++;
            if (bAttr) {
                XML.WriteXmlElement("<reserved1 type=\"flUInt\" bitLen=\"" + _F_parse + "\">" + reserved1 + "</reserved1>");
            }
            else {
                XML.WriteXmlElement("<reserved1 bitLen=\"" + _F_parse + "\">" + reserved1 + "</reserved1>");
            }
        }
        else {
            if (bAttr) {
                XML.WriteXmlElement("<reserved1 type=\"flUInt\" bitLen=\"" + _F_parse + "\">" + reserved1 + "</reserved1>");
            }
            else {
                XML.WriteXmlElement("<reserved1 bitLen=\"" + _F_parse + "\">" + reserved1 + "</reserved1>");
            }
            Util.trace(_F_bs.getpos()-_F_parse, _F_parse, (int)reserved1, "reserved1" +  " (" + reserved1 + ")");
        }
        _F_parse = 13;
        table_type_PID = _F_bs.getbits(_F_parse);
        if (bAttr) {
            XML.WriteXmlElement("<table_type_PID type=\"flUInt\" bitLen=\"" + _F_parse + "\">" + table_type_PID + "</table_type_PID>");
        }
        else {
            XML.WriteXmlElement("<table_type_PID bitLen=\"" + _F_parse + "\">" + table_type_PID + "</table_type_PID>");
        }
        _F_parse = 3;
        reserved2 = _F_bs.getbits(_F_parse);
        if (reserved2 != 7) {
            Util.flerror("Const value mismatch for 'reserved2'");
            _F_ret++;
            if (bAttr) {
                XML.WriteXmlElement("<reserved2 type=\"flUInt\" bitLen=\"" + _F_parse + "\">" + reserved2 + "</reserved2>");
            }
            else {
                XML.WriteXmlElement("<reserved2 bitLen=\"" + _F_parse + "\">" + reserved2 + "</reserved2>");
            }
        }
        else {
            if (bAttr) {
                XML.WriteXmlElement("<reserved2 type=\"flUInt\" bitLen=\"" + _F_parse + "\">" + reserved2 + "</reserved2>");
            }
            else {
                XML.WriteXmlElement("<reserved2 bitLen=\"" + _F_parse + "\">" + reserved2 + "</reserved2>");
            }
            Util.trace(_F_bs.getpos()-_F_parse, _F_parse, (int)reserved2, "reserved2" +  " (" + reserved2 + ")");
        }
        _F_parse = 5;
        table_type_version_number = _F_bs.getbits(_F_parse);
        if (bAttr) {
            XML.WriteXmlElement("<table_type_version_number type=\"flUInt\" bitLen=\"" + _F_parse + "\">" + table_type_version_number + "</table_type_version_number>");
        }
        else {
            XML.WriteXmlElement("<table_type_version_number bitLen=\"" + _F_parse + "\">" + table_type_version_number + "</table_type_version_number>");
        }
        _F_parse = 32;
        number_bytes = _F_bs.getbits(_F_parse);
        if (bAttr) {
            XML.WriteXmlElement("<number_bytes type=\"flUInt\" bitLen=\"" + _F_parse + "\">" + number_bytes + "</number_bytes>");
        }
        else {
            XML.WriteXmlElement("<number_bytes bitLen=\"" + _F_parse + "\">" + number_bytes + "</number_bytes>");
        }
        _F_parse = 4;
        reserved3 = _F_bs.getbits(_F_parse);
        if (reserved3 != 15) {
            Util.flerror("Const value mismatch for 'reserved3'");
            _F_ret++;
            if (bAttr) {
                XML.WriteXmlElement("<reserved3 type=\"flUInt\" bitLen=\"" + _F_parse + "\">" + reserved3 + "</reserved3>");
            }
            else {
                XML.WriteXmlElement("<reserved3 bitLen=\"" + _F_parse + "\">" + reserved3 + "</reserved3>");
            }
        }
        else {
            if (bAttr) {
                XML.WriteXmlElement("<reserved3 type=\"flUInt\" bitLen=\"" + _F_parse + "\">" + reserved3 + "</reserved3>");
            }
            else {
                XML.WriteXmlElement("<reserved3 bitLen=\"" + _F_parse + "\">" + reserved3 + "</reserved3>");
            }
            Util.trace(_F_bs.getpos()-_F_parse, _F_parse, (int)reserved3, "reserved3" +  " (" + reserved3 + ")");
        }
        _F_parse = 12;
        table_type_descriptors_length = _F_bs.getbits(_F_parse);
        if (bAttr) {
            XML.WriteXmlElement("<table_type_descriptors_length type=\"flUInt\" bitLen=\"" + _F_parse + "\">" + table_type_descriptors_length + "</table_type_descriptors_length>");
        }
        else {
            XML.WriteXmlElement("<table_type_descriptors_length bitLen=\"" + _F_parse + "\">" + table_type_descriptors_length + "</table_type_descriptors_length>");
        }
        remain_desc_bytes = table_type_descriptors_length;
        while (remain_desc_bytes>0) {
            Util.trace(_F_bs.getpos(), 0, (int)0, "processing Descriptor descriptor");
            descriptor = new Descriptor();
            XML.IntoAClass("descriptor", 0);
            _F_ret += descriptor.putxml(_F_bs, bAttr);
            XML.OutOfClass("</descriptor>");
            remain_desc_bytes-=(descriptor._F_lengthof/8);
            if (1==0) break;
        }
        return _F_ret;
    }
}
