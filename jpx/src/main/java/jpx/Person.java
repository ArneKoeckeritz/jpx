/*
 * Java GPX Library (@__identifier__@).
 * Copyright (c) @__year__@ Franz Wilhelmstötter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author:
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmail.com)
 */
package jpx;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * A person or organization.
 *
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 * @version !__version__!
 * @since !__version__!
 */
public final class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String _name;
	private final Email _email;
	private final Link _link;

	/**
	 * Create a new {@code Person} object with the given parameters.
	 *
	 * @param name name of person or organization
	 * @param email the person's email address
	 * @param link link to Web site or other external information about person
	 */
	private Person(final String name, final Email email, final Link link) {
		_name = name;
		_email = email;
		_link = link;
	}

	/**
	 * Return the name of the person or organization.
	 *
	 * @return the name of the person or organization
	 */
	public Optional<String> getName() {
		return Optional.ofNullable(_name);
	}

	/**
	 * Return the email address.
	 *
	 * @return the email address
	 */
	public Optional<Email> getEmail() {
		return Optional.ofNullable(_email);
	}

	/**
	 * Return the link to Web site or other external information about person.
	 *
	 * @return the link to Web site or other external information about person
	 */
	public Optional<Link> getLink() {
		return Optional.ofNullable(_link);
	}

	@Override
	public int hashCode() {
		int hash = 37;
		hash += 17*Objects.hashCode(_name) + 31;
		hash += 17*Objects.hashCode(_email) + 31;
		hash += 17*Objects.hashCode(_link) + 31;
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof Person &&
			Objects.equals(((Person)obj)._name, _name) &&
			Objects.equals(((Person)obj)._email, _email) &&
			Objects.equals(((Person) obj)._link, _link);
	}

	@Override
	public String toString() {
		return _name;
	}

	/* *************************************************************************
	 *  Static object creation methods
	 * ************************************************************************/

	/**
	 * Create a new {@code Person} object with the given parameters.
	 *
	 * @param name name of person or organization
	 * @param email the person's email address
	 * @param link link to Web site or other external information about person
	 * @return a new {@code Person} object with the given parameters
	 */
	public static Person of(final String name, final Email email, final Link link) {
		return new Person(name, email, link);
	}

	/**
	 * Create a new {@code Person} object with the given parameters.
	 *
	 * @param name name of person or organization
	 * @param email the person's email address
	 * @return a new {@code Person} object with the given parameters
	 */
	public static Person of(final String name, final Email email) {
		return new Person(name, email, null);
	}

	/**
	 * Create a new {@code Person} object with the given parameters.
	 *
	 * @param name name of person or organization
	 * @return a new {@code Person} object with the given parameters
	 */
	public static Person of(final String name) {
		return new Person(name, null, null);
	}

	/**
	 * Create a new <i>empty</i> {@code Person}.
	 *
	 * @return a new <i>empty</i> {@code Person}
	 */
	public static Person of() {
		return new Person(null, null, null);
	}

	/* *************************************************************************
	 *  XML stream object serialization
	 * ************************************************************************/

	/**
	 * Writes this {@code Link} object to the given XML stream {@code writer}.
	 *
	 * @param writer the XML data sink
	 * @throws XMLStreamException if an error occurs
	 */
	void write(final XMLStreamWriter writer) throws XMLStreamException {
		final XMLWriter xml = new XMLWriter(writer);

		xml.write("person",
			xml.elem("name", _name),
			xml.elem(_email, Email::write),
			xml.elem(_link, Link::write)
		);
	}

	static XMLReader<Person> reader() {
		final Function<Object[], Person> creator = a -> Person.of(
			(String)a[0], (Email)a[1], (Link)a[2]
		);

		return XMLReader.of(creator, "person",
			XMLReader.of("name"),
			Email.reader(),
			Link.reader()
		);
	}

}