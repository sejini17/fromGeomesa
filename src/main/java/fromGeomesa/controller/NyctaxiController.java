package fromGeomesa.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fromGeomesa.config.impl.NycTaxiDataConfig;
import fromGeomesa.pojo.dto.GeoMesaDTO;
import fromGeomesa.service.INyctaxiService;

import java.util.HashMap;


@RestController
@CrossOrigin    //解决跨域
@RequestMapping(value = "/nyctaxi")
public class NyctaxiController {

    private final static Logger logger = LoggerFactory.getLogger(NyctaxiController.class);

    @Autowired
    private INyctaxiService iNyctaxiService;

    @Autowired
    private NycTaxiDataConfig nycTaxiDataConfig;

    /* 测试 */
    @GetMapping(value = "/hello/{id}")
    public String hello(@PathVariable("id")int id,@RequestParam("name")String name,@RequestParam("age")int age){
        logger.info("测试数据");
        return "ID: "+id+"姓名： "+name+",年龄："+age;
    }
    @GetMapping(value = "/hello")
    public String hello1(){
        logger.info("测试数据");
        return "hello";
    }

    @GetMapping(value = "/insert/{catalogName}")
    public String insertNyctaxi(@PathVariable("catalogName") String catalogName){
        logger.info(catalogName+" 插入数据...");
        GeoMesaDTO geoMesaDTO =new GeoMesaDTO();
        geoMesaDTO.setParams(new HashMap<String, String>(){
            {
                put("hbase.catalog", catalogName);
            }
        });
        geoMesaDTO.setData(nycTaxiDataConfig);
        Boolean status=iNyctaxiService.insertNyctaxiData(geoMesaDTO.getParams());
        if(status==true){
            return "成功";
        }else{
            return "失败";
        }
    }

    @GetMapping(value = "/delete/{catalogName}")
    public String deleteNyctaxi(@PathVariable("catalogName")String catalogName){
        logger.info(catalogName+" 删除数据...");
        GeoMesaDTO geoMesaDTO =new GeoMesaDTO();
        geoMesaDTO.setParams(new HashMap<String, String>(){
            {
                put("hbase.catalog", catalogName);
            }
        });
        geoMesaDTO.setData(nycTaxiDataConfig);
        Boolean status=iNyctaxiService.deleteNyctaxiDatastore(geoMesaDTO.getParams());
        if(status==true){
            return "成功";
        }else {
            return "失败";
        }
    }

    @GetMapping(value = "/query/SpatiotemporalQuery/{catalogName}")
    public String spatiotemporalQuery(@PathVariable("catalogName")String catalogName){
        logger.info(catalogName+" 时空查询...");
        GeoMesaDTO geoMesaDTO =new GeoMesaDTO();
        geoMesaDTO.setParams(new HashMap<String, String>(){
            {
                put("hbase.catalog", catalogName);
            }
        });
        geoMesaDTO.setData(nycTaxiDataConfig);
        String nyctaxiGeoJson=iNyctaxiService.spatiotemporalQuery(geoMesaDTO.getParams());
        if(nyctaxiGeoJson!=null){
            return nyctaxiGeoJson;
        }else {
            return "错误";
        }
    }

    @GetMapping(value = "/query/SpatiotemporalQueryWithField/{catalogName}")
    public String spatiotemporalWithAttribute(@PathVariable("catalogName")String catalogName) {
        logger.info(catalogName+" 时空查询带字段...");
        GeoMesaDTO geoMesaDTO =new GeoMesaDTO();
        geoMesaDTO.setParams(new HashMap<String, String>(){
            {
                put("hbase.catalog", catalogName);
            }
        });
        geoMesaDTO.setData(nycTaxiDataConfig);
        String nyctaxiGeoJsonWithAttribute=iNyctaxiService.spatiotemporalQueryWithField(geoMesaDTO.getParams());
        if(nyctaxiGeoJsonWithAttribute!=null){
            return nyctaxiGeoJsonWithAttribute;
        }else {
            return "错误";
        }
    }

    @GetMapping(value = "/query/AttributeQuery/{catalogName}")
    public String queryAttribute(@PathVariable("catalogName")String catalogName){
        logger.info(catalogName+" 简单属性查询...");
        GeoMesaDTO geoMesaDTO =new GeoMesaDTO();
        geoMesaDTO.setParams(new HashMap<String, String>(){
            {
                put("hbase.catalog", catalogName);
            }
        });
        geoMesaDTO.setData(nycTaxiDataConfig);
        String nyctaxiGeoJson=iNyctaxiService.attributeQuery(geoMesaDTO.getParams());
        if(nyctaxiGeoJson!=null){
            return nyctaxiGeoJson;
        }else {
            return "错误";
        }
    }

    @GetMapping(value = "/query/SpatiotemporalAttributeWithField/{catalogName}")
    public String spatiotemporalAttributeWithField(@PathVariable("catalogName")String catalogName){
        logger.info(catalogName+" 时空及属性带字段查询...");
        GeoMesaDTO geoMesaDTO =new GeoMesaDTO();
        geoMesaDTO.setParams(new HashMap<String, String>(){
            {
                put("hbase.catalog", catalogName);
            }
        });
        geoMesaDTO.setData(nycTaxiDataConfig);
        String nyctaxiGeoJson=iNyctaxiService.spatiotemporalAttributeWithField(geoMesaDTO.getParams());
        if(nyctaxiGeoJson!=null){
            return nyctaxiGeoJson;
        }else {
            return "错误";
        }
    }

    @GetMapping("/query/SpatiotemporalAttributeParam")
    public String spatiotemporalAttributeParam(@RequestParam("catalog")String catalog,@RequestParam("space")String space,
                                     @RequestParam("start_timedate")String start_timedate,@RequestParam("end_timedate")String end_timedate,
                                                   @RequestParam("attribute")String attribute)
    {
        logger.info(catalog+" 时空及属性交互设置...");
        GeoMesaDTO geoMesaDTO =new GeoMesaDTO();
        geoMesaDTO.setParams(new HashMap<String, String>(){
            {
                put("hbase.catalog", catalog);
            }
        });
        geoMesaDTO.setData(nycTaxiDataConfig);
        String nyctaxiGeoJson=iNyctaxiService.spatiotemporalAttributeParam(geoMesaDTO.getParams(),space,
                start_timedate,end_timedate,attribute);
        if(nyctaxiGeoJson!=null){
            return nyctaxiGeoJson;
        }else {
            return "错误";
        }
    }

    @GetMapping("/query/SpatiotemporalParam")
    public String spatiotemporalParam(@RequestParam("catalog")String catalog,@RequestParam("space")String space,
                                               @RequestParam("start_timedate")String start_timedate,@RequestParam("end_timedate")String end_timedate)
    {
        logger.info(catalog+" 时空交互设置...");
        GeoMesaDTO geoMesaDTO =new GeoMesaDTO();
        geoMesaDTO.setParams(new HashMap<String, String>(){
            {
                put("hbase.catalog", catalog);
            }
        });
        geoMesaDTO.setData(nycTaxiDataConfig);
        String nyctaxiGeoJson=iNyctaxiService.spatiotemporalParam(geoMesaDTO.getParams(),space,
                start_timedate,end_timedate);
        if(nyctaxiGeoJson!=null){
            return nyctaxiGeoJson;
        }else {
            return "错误";
        }
    }

    @PostMapping(value = "/queryNyctaxi/all/{catalogName}")
    public void queryAllNyctaxi(@PathVariable("catalogName")String catalogName){
        logger.info(catalogName+" 正在查询...");
        GeoMesaDTO geoMesaDTO =new GeoMesaDTO();
        geoMesaDTO.setParams(new HashMap<String, String>(){
            {
                put("hbase.catalog", catalogName);
            }
        });
        geoMesaDTO.setData(nycTaxiDataConfig);
        iNyctaxiService.queryNyctaxiData(geoMesaDTO.getParams());
    }
}
