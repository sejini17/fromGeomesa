<template>
  <div>CQL : <input id="txtCql"
        v-model="cql"
        type="text">
      <button
        name="button"
        @click="execCql">Execute CQL</button>
    <span v-if="loading">Loading...</span>
    {{errorMsg}}
    <br><br>

    <div id='map'>
      <p id='latLng'>Lat: {{lat}} Lng: {{lng}}</p>
      <form id='mapType'>
        <label>Change Map Type</label>
        <input type='radio' name='mapType' value='default' v-model='mapType' v-on:change='changeMapType' /> Default <br />
        <input type='radio' name='mapType' value='satellite' v-model='mapType' v-on:change='changeMapType' /> Satellite <br />
      </form>
    </div>
  </div>
</template>

<script>
// import L from 'leaflet'

const config = {
  mapboxToken: 'pk.eyJ1Ijoic2VqaW5pMTciLCJhIjoiY2p1bWJ2Y2MxMDZkZzN5cGlid3l5ZHczNSJ9.VkQuTJCI4p6PVfua2dnVVg',
  mapStyles: {
    default: 'mapbox://styles/mapbox/streets-v11',
    satellite: 'mapbox://styles/mapbox/satellite-v9'
  }
}
const defaultLatLng = {
  // lng = -73.7593, lat = 41.0294;
  lat: 41.0294, lng: -73.7593
  // lat: 37.7720566, lng: -122.4099524 // 미국
  // lat: 37.7720566, lng: 127.4099524 // 우리나라
}
const defaultOptions = {
  container: 'map',
  style: config.mapStyles.default,
  center: [defaultLatLng.lng, defaultLatLng.lat],
  zoom: 7,
  attributionControl: false
}

const mapboxgl = require('mapbox-gl')
mapboxgl.accessToken = config.mapboxToken

export default {
  data () {
    return {
      route: [],
      lat: defaultLatLng.lat,
      lng: defaultLatLng.lng,
      mapType: 'default',
      cql: 'BBOX(geom, -73.7593,41.0294,-71.7593,43.0294)',
      loading: false,
      errorMsg: ''
    }
  },
  mounted () {
    this.map = new mapboxgl.Map(defaultOptions)
    this.map.addControl(new mapboxgl.NavigationControl())
    this.map.addControl(new mapboxgl.FullscreenControl())
    this.loadData()
  },
  methods: {
    changeMapType (event) {
      this.map.setStyle(config.mapStyles[this.mapType])
    },
    loadData () {
      const map = this.map
      // let nav = new mapboxgl.NavigationControl({position: 'bottom-right'})
      // map.addControl(nav)
      // Add zoom and rotation controls to the map.
      map.addControl(new mapboxgl.NavigationControl())
      // Add geolocate control to the map.
      map.addControl(new mapboxgl.GeolocateControl({
        positionOptions: {
          enableHighAccuracy: true
        },
        trackUserLocation: true
      }))
      map.addControl(new mapboxgl.FullscreenControl())
      // map.addControl(new MapboxGeocoder({
      //   accessToken: mapboxgl.accessToken,
      //   mapboxgl: mapboxgl
      // }))

      map.on('load', () => {
        map.addSource('resultFeatures', {
          'type': 'geojson',
          'data': {
            'type': 'FeatureCollection',
            'features': [ ]
          }
        })
        map.addLayer({
          'id': 'resultFeatures',
          'type': 'symbol',
          'source': 'resultFeatures',
          'layout': {
            'icon-image': 'rocket-15',
            'text-field': '오잉',
            'text-font': ['Open Sans Semibold', 'Arial Unicode MS Bold'],
            'text-offset': [0, 0.6],
            'text-anchor': 'top'
          }
        })
      })
    },
    execCql () {
      this.loading = true
      this.$http.get('/api/query', {
        params: { cql: this.cql }
      }).then(response => {
        this.map.getSource('resultFeatures').setData(response.data)
        // console.log('response.data', response.data)
        this.loading = false
        this.errorMsg = ''
      }).catch(reason => {
        console.log(reason)
        this.errorMsg = reason
        this.loading = false
      })
    }
  }
}
</script>

<style scoped lang='scss'>
#map {
  position:absolute;
  top:50px;
  bottom:0;
  width:90%;
  height: 500px;
}

#latLng {
  background: transparentize(white, 0.2);
  bottom: 0;
  box-sizing: border-box;
  display: inline;
  left: 10px;
  padding: 5px;
  pointer-events: none;
  position: absolute;
  z-index: 200;
}

#mapType {
  background: white;
  border-radius: 5px;
  box-sizing: border-box;
  display: inline;
  padding: 5px;
  top: 10px;
  right: 10px;
  position: absolute;
  z-index: 200;

  label {
    display: block;
    font-weight: bold;
  }
}

#txtCql {
  width: 500px;
}
</style>
