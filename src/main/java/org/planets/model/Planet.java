package org.planets.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Planet implements Serializable {

    @Id
    @TableGenerator(name="TABLE_GEN",table="TABLE_GENERATOR", pkColumnName = "GEN_KEY", pkColumnValue = "PLANETS.GENERATOR", valueColumnName = "GEN_VALUE", initialValue = 1, allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.TABLE, generator="TABLE_GEN")
    private Long id;
    @Version
    @Column(name = "version")
    private int version;

    @Column
    private String name;

    @Column
    private String galaxy;

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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGalaxy() {
        return galaxy;
    }

    public void setGalaxy(String galaxy) {
        this.galaxy = galaxy;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (name != null && !name.trim().isEmpty())
            result += "name: " + name;
        if (galaxy != null && !galaxy.trim().isEmpty())
            result += ", galaxy: " + galaxy;
        return result;
    }
}
