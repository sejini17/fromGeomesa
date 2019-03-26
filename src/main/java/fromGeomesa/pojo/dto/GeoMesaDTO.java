package fromGeomesa.pojo.dto;


import java.util.Map;

import fromGeomesa.config.IGeoMesaDataConfig;

public class GeoMesaDTO {

    private Map<String, String> params;
    private IGeoMesaDataConfig data;

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public IGeoMesaDataConfig getData() {
        return data;
    }

    public void setData(IGeoMesaDataConfig data) {
        this.data = data;
    }
}
