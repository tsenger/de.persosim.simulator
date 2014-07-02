package de.persosim.simulator.test.globaltester.perso;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.persosim.simulator.jaxb.PersoSimJaxbContextProvider;
import de.persosim.simulator.perso.DefaultPersonalization;
import de.persosim.simulator.perso.Personalization;
import de.persosim.simulator.perso.XmlPersonalisation;

/**
 * Marshall/unmarshall the DefaultPersonalisation and check it afterwards
 * against the GlobalTester. This ensures that the serialization process does
 * not loose data.
 * 
 * @author amay
 * 
 */
public class GtXmlDefaultPersoTest extends GtDefaultPersoTest {

	@Override
	public Personalization getPersonalization() {
		DefaultPersonalization defaultPerso = new DefaultPersonalization();

		XmlPersonalisation xmlPerso = new XmlPersonalisation();
		xmlPerso.setProtocolList(defaultPerso.getProtocolList());
		xmlPerso.setMf(defaultPerso.getObjectTree());

		XmlPersonalisation unmarshalledPerso = null;
		try {
			// instantiate marshaller
			Marshaller m = PersoSimJaxbContextProvider.getContext()
					.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// marshall the perso to StringWriter
			StringWriter strWriter = new StringWriter();
			m.marshal(xmlPerso, strWriter);

			// unmarshall the perso from StringReader
			StringReader sr = new StringReader(strWriter.toString());
			Unmarshaller um = PersoSimJaxbContextProvider.getContext()
					.createUnmarshaller();
			unmarshalledPerso = (XmlPersonalisation) um.unmarshal(sr);
		} catch (JAXBException e) {
			//forward the exception as RuntimeException to make the testcase fail
			throw new RuntimeException(e);
		}

		return unmarshalledPerso;
	}
}