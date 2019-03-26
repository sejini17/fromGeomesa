package fromGeomesa.config;

import org.geotools.data.Query;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;

import java.util.List;

public interface IGeoMesaDataConfig {
    String getTypeName();
    SimpleFeatureType getSimpleFeatureType();
    List<SimpleFeature> getData();
    List<Query> getQueries();
    Filter getSubsetFilter();
}
