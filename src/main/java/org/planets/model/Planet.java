package org.planets.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
@Entity
public class Planet implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column
	private String Name;

	@Column
	private String Galaxy;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Planet)) {
			return false;
		}
		Planet other = (Planet) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getGalaxy() {
		return Galaxy;
	}

	public void setGalaxy(String Galaxy) {
		this.Galaxy = Galaxy;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (Name != null && !Name.trim().isEmpty())
			result += "Name: " + Name;
		if (Galaxy != null && !Galaxy.trim().isEmpty())
			result += ", Galaxy: " + Galaxy;
		return result;
	}
}