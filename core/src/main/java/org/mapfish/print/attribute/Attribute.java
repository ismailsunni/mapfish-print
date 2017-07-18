package org.mapfish.print.attribute;

import org.json.JSONException;
import org.json.JSONWriter;
import org.mapfish.print.config.ConfigurationObject;
import org.mapfish.print.config.Template;

/**
 * Represents an attribute passed in from a web-client to be used to populate the report.  It reads a value from the request data
 * <p></p>
 */
public interface Attribute extends ConfigurationObject {

    /**
     * Write this attribute out the the json writer so that clients can know what attributes are expected.
     *
     * @param json the json writer to write to
     * @param template the template that this attribute is part of
     * @throws JSONException
     */
    void printClientConfig(JSONWriter json, Template template) throws JSONException;
    /**
     * Set the name of the attribute as set in the configuration file.
     *
     * @param name the name of the attribute
     */
    void setConfigName(String name);

    /**
     * Get the class of the value.
     *
     * @return the value class
     */
    Class getValueType();
}