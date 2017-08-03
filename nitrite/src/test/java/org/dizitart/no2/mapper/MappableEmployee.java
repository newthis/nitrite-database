package org.dizitart.no2.mapper;

import lombok.Data;
import lombok.ToString;
import org.dizitart.no2.Document;

import java.util.*;

/**
 * @author Anindya Chatterjee
 */
@Data
@ToString
public class MappableEmployee implements Mappable {
    private String empId;
    private String name;
    private Date joiningDate;
    private MappableEmployee boss;

    @Override
    public Document write(NitriteMapper mapper) {
        Document document = new Document();
        document.put("empId", getEmpId());
        document.put("name", getName());
        document.put("joiningDate", getJoiningDate());

        if (getBoss() != null) {
            Document bossDoc = getBoss().write(mapper);
            document.put("boss", bossDoc);
        }
        return document;
    }

    @Override
    public void read(NitriteMapper mapper, Document document) {
        if (document != null) {
            setEmpId((String) document.get("empId"));
            setName((String) document.get("name"));
            setJoiningDate((Date) document.get("joiningDate"));

            Document bossDoc = (Document) document.get("boss");
            if (bossDoc != null) {
                MappableEmployee bossEmp = new MappableEmployee();
                bossEmp.read(mapper, bossDoc);
                setBoss(bossEmp);
            }
        }
    }
}