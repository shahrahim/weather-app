package com.project.weatherapp.config

data class LocationConfig (
    var geoLocationUrl: String = "https://www.googleapis.com/geolocation/v1/geolocate",
    var geoCodeUrl: String = "https://maps.googleapis.com/maps/api/geocode/json",
    var key: String = "AIzaSyByVIYzUFM9Nn4jK3jux8-q7S7S8b9N_y8"
)