import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Location } from './home/home.component';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Geolocation } from '@capacitor/geolocation';

@Injectable({
  providedIn: 'root',
})
export class LocationService {
  abstractApiUrl: string = environment.abstractApiUrl;

  constructor(private _httpClient: HttpClient) {}

  getCurrentLocation(): Promise<Location> {
    return new Promise((resolve, reject) => {
      navigator.geolocation.getCurrentPosition(
        (curLocation) => {
          resolve(
            new Location(
              curLocation.coords.latitude,
              curLocation.coords.longitude
            )
          );
          console.log();
          curLocation.coords.latitude + '-' + curLocation.coords.longitude;
        },
        (error) => {
          reject(environment.locationErrorMsg);
        }
      );
    });
  }

  getCurrentLocationGeolocation(): Promise<any> {
    return Geolocation.getCurrentPosition({ enableHighAccuracy: true });
  }
}
