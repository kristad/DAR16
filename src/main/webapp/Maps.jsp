

<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>Geocoding service</title>
    <style>
      html, body, #map-canvas {
        position: absolute;
        height: 300px;
        width: 300px;
        left: 0px;
        padding: 0px
      }
      #panel {
        position: absolute;
        top: 5px;
        left: 100%;
        margin-left: -180px;
        z-index: 5;
        background-color: #fff;
        padding: 5px;
        border: 1px solid #999;
      }
      #panel2 {
        position: absolute;
        top: 500px;
        height: 300px; 
        width: 700px
        left: 0px;
        margin-left: 0px;
        z-index: 5;
        background-color: #fff;
        padding: 5px;
        border: 1px solid #999;
      }
    </style>
          <div id="panel2" >ici</div>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script>

var geocoder;
var map;
function initialize() {
  geocoder = new google.maps.Geocoder();
  var latlng = new google.maps.LatLng(48.856614, 2.3522219000000177);
  var mapOptions = {
    zoom: 8,
    center: latlng
  }
  map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
}


function codeAddress() {

  var address = document.getElementById('address').value;
  geocoder.geocode( { 'address': address}, function(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {
      map.setCenter(results[0].geometry.location);
      var lat=results[0].geometry.location.lat();
      var lng=results[0].geometry.location.lng();
      var url="Trajet?lat="+lat+"&lng="+lng;
      	$("#panel2").load( url, function() { // ici c'est get car pas de parametres dans load, mettre le nom d'une servlet avec les coordonnées, comment la session sera géré ?
		//alert( "Load was performed." );
		});
      var marker = new google.maps.Marker({
          map: map,
          position: results[0].geometry.location
      });
    } else {
      alert('Geocode was not successful for the following reason: ' + status);
    }
  });
}

google.maps.event.addDomListener(window, 'load', initialize);

    </script>
      
  </head>
  <body>
  	
    <div id="map-canvas"></div>   
    <div id="panel"><input id="address" type="text" value="Paris, fr"><input type="button" value="Geocode" onclick="codeAddress()"></div>

      <div id="panel2" >ici</div>

  </body>
</html>

