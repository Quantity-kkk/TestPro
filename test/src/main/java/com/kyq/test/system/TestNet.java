package com.kyq.test.system;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.RetrievalEvent;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Description:
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/9/10
 */
public class TestNet {
    public static void main(String args[]){
        System.out.println(getBandwidth());
    }

    static  String  netCardName= "";
    public static String getBandwidth()  {
        Snmp snmp = null;
        try {
            snmp = new Snmp(new DefaultUdpTransportMapping());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            snmp.listen();
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString("public"));
            target.setVersion(SnmpConstants.version2c);
            target.setAddress(new UdpAddress("127.0.0.1/161"));
            target.setTimeout(6000);    //3s
            target.setRetries(3);
            String variable = "0.00";

            TableUtils utils = new TableUtils(snmp, new DefaultPDUFactory(PDU.GETBULK));//GETNEXT or GETBULK
            utils.setMaxNumRowsPerPDU(5);   //only for GETBULK, set max-repetitions, default is 10
            OID[] columnOids = new OID[]{
                    new OID("1.3.6.1.2.1.2.2.1.2"), //sysORDescr
                    new OID("1.3.6.1.2.1.2.2.1.5") //sysORID
            };
            // If not null, all returned rows have an index in a range (lowerBoundIndex, upperBoundIndex]
            List<TableEvent> l = utils.getTable(target, columnOids, new OID("0"), new OID("39"));
            for (TableEvent e : l) {
                if (e.getStatus() == RetrievalEvent.STATUS_OK) {
                    VariableBinding[] columns = e.getColumns();
                    int flag = 0;
                    for (int i = 0; i < columns.length; i++) {
                        if (columns[i].getOid().toString().contains("1.3.6.1.2.1.2.2.1.2")) {
                            if (netCardName.equals(decode(columns[i].getVariable().toString()))) {
                                flag = 1;
                            }
                        }
                        if (columns[i].getOid().toString().contains("1.3.6.1.2.1.2.2.1.5")) {
                            variable = columns[i].getVariable().toString();
                        }
                    }
                    if (flag == 1) {
                        break;
                    }
                }
            }

            DecimalFormat df = new DecimalFormat("#.##");
            return df.format((float)Double.parseDouble(variable) / 1000);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                snmp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     */
    public static String decode(String bytes) {
        if (!bytes.contains(":")) {
            return bytes;
        }
        String[] hexs = bytes.split(":");
        ByteArrayOutputStream baos = new ByteArrayOutputStream(hexs.length);
        for (String hex : hexs) {
            baos.write((byte)Integer.parseInt(hex,16));
        }
        return new String(baos.toByteArray(), Charset.forName("GBK")).trim();
    }
}

