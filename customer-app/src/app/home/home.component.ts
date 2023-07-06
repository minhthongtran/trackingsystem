import { Component, OnInit } from '@angular/core';
import * as mapboxgl from 'mapbox-gl';
import { environment } from 'src/environments/environment';
import { LocationService } from '../location.service';
import * as MapboxDirections from '@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions';

export class Location {
  #latitude!: number;
  #longitude!: number;

  constructor(long: number, lat: number) {
    this.#latitude = lat;
    this.#longitude = long;
  }

  get latitude() {
    return this.#latitude;
  }

  set latitude(lat: number) {
    this.#latitude = lat;
  }

  get longitude() {
    return this.#longitude;
  }

  set longitude(long: number) {
    this.#longitude = long;
  }
}
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  map!: mapboxgl.Map;
  style = environment.mapDefaultStyle;
  lat = environment.zero;
  lng = environment.zero;
  count = 0;
  constructor(private _locationService: LocationService) {}
  ngOnInit(): void {
    // setInterval(
    //   async () => {
    //     this._updateLocation(this._locationService, this._createMap);
    //   },
    //   2000,
    //   true
    // );
    this._updateLocation(this._locationService, this._createMap);
  }

  _createMap = (location: Location) => {
    this.lat = location.latitude;
    this.lng = location.longitude;
    if (!this.map) {
      this.map = new mapboxgl.Map({
        container: environment.mapContainer,
        style: environment.mapDefaultStyle,
        zoom: environment.defaultZoom,
        accessToken: environment.mapbox.accessToken,
      });
      const geolocate = new mapboxgl.GeolocateControl({
        positionOptions: {
          enableHighAccuracy: true,
        },
        trackUserLocation: true,
        showUserLocation: true,
      });
      const directions = new MapboxDirections({
        accessToken: environment.mapbox.accessToken,
        unit: 'metric',
        profile: 'mapbox/driving',
        container: 'directions',
        bearing: false,
        steps: false,
        voice_instruction: true,
        controls: {
          inputs: false,
          instructions: false,
          profileSwitcher: false,
        },
      });

      this.map.addControl(directions, 'bottom-left');
      this.map.addControl(geolocate);
      geolocate.on('geolocate', function (e) {
        console.log('Lat: ' + e + ' Long: ' + e);
      });
      this.map.on('load', () => {
        directions.setOrigin([location.longitude, location.latitude]);
        directions.setDestination([-91.9699327, 41.0179095]);
        geolocate.trigger();
      });
    }
  };

  _locateUser = function (e: any) {};
  _removeSourceLayer() {
    return new Promise((resolve, reject) => {
      this.map.removeLayer('iss');
      this.map.removeSource('iss');
      resolve(true);
    });
  }

  _displayIcon(location: Location) {
    this.map.on('load', async () => this._displayResource(location));
  }

  _displayResource(location: Location) {
    this.map.addSource('iss', {
      type: 'geojson',
      data: {
        type: 'FeatureCollection',
        features: [
          {
            type: 'Feature',
            geometry: {
              type: 'Point',
              coordinates: [location.longitude, location.latitude],
            },
            properties: {},
          },
        ],
      },
    });
    // Add the rocket symbol layer to the map.
    this.map.addLayer({
      id: 'iss',
      type: 'symbol',
      source: 'iss',
      layout: {
        'icon-image': 'rocket',
        'icon-size': 1.2,
      },
    });
    this.map.flyTo({
      center: [location.longitude, location.latitude],
      speed: 16.5,
    });
  }

  _updateLocation = function (
    locationService: LocationService,
    createUpdateMap: any
  ) {
    locationService
      .getCurrentLocationGeolocation()
      .then((resp) => createUpdateMap(resp.coords))
      .catch((error) => console.log(error));
  };
}
