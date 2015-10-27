package de.fraunhofer.iosb.tc_lib;

import hla.rti1516e.ObjectClassHandle;


/**
 * @author mul (Fraunhofer IOSB)
 */
public class ObjectData {
    //    AttributeDataMap  attributeDataMap;
    // Whether uuidValue16 has been set
    boolean           isUuidSet;
    boolean           needUpdate;
    ObjectClassHandle objectClassHandle;
    String            uuidString;
    String            name;

    byte[]            uuidValue16 = new byte[16];


    ObjectData(final ObjectClassHandle objectClassHandle, final String name) {
        this.name = name;
        this.isUuidSet = false;
        this.needUpdate = true;
        this.objectClassHandle = objectClassHandle;
    }

    //    void buildAttributeData(AttributeHandleSet attributeHandleSet, boolean isOwner);

    //    void getAttributeNamesMatched(AttributeHandleSet attributeHandleSet, WstringSet attributeNames, AttributeNameMap attributeNameMap);

    //    void getAttributeValues(AttributeHandleSet attributes, AttributeHandleValueMap attributeValues);

    //    void getAttributeValues(AttributeHandleValueMap attributeHandleValueMap);


    //    void getAttributeValuesOwned(RTIambassador rtiAmbassador, boolean allBool, ObjectInstanceHandle objectInstanceHandle, AttributeHandleValueMap attributeHandleValueMap);

    boolean getNeedUpdate() {
        return this.needUpdate;
    }


    ObjectClassHandle getObjectClassHandle() {
        return this.objectClassHandle;
    }


    boolean getUuidSet() {
        return this.isUuidSet;
    }


    String getUuidString() {
        return this.uuidString;
    }


    void setNeedUpdate(final boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    /*
     * void setOwnership(final AttributeHandleSet attributes, final boolean
     * isOwned) { } void setUUID(final String uuidString, final AttributeNameMap
     * attributeNameMap) { } void updateObject(final AttributeHandleValueMap
     * attributeValues, final AttributeNameMap attributeNameMap) { }
     */
}
