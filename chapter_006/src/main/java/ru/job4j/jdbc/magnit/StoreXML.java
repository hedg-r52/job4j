package ru.job4j.jdbc.magnit;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

public class StoreXML {
    private File target;

    public StoreXML(File target) {
        this.target = target;
    }

    public void save(Entries entries) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Entries.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(entries, target);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
