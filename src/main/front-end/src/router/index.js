import Vue from 'vue'
import Router from 'vue-router'

// import HelloWorld from '@/components/HelloWorld'
import Mapbox2 from '@/components/Mapbox2'
import MyLeaflet from '@/components/MyLeaflet'
import TestGeoMesa from '@/components/TestGeoMesa'

Vue.use(Router)

export default new Router({
  routes: [
    // {
    //   path: '/',
    //   name: 'HelloWorld',
    //   component: HelloWorld
    // },
    {
      path: '/',
      name: 'TestMap',
      component: TestGeoMesa
    },
    {
      path: '/mapbox',
      name: 'TestMap',
      component: Mapbox2
    },
    {
      path: '/leaflet',
      name: 'TestMap',
      component: MyLeaflet
    }
  ]
})
