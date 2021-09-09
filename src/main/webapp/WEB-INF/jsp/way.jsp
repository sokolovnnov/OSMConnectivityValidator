<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"/>
    <script src="https://unpkg.com/leaflet@1.6.0/dist/leaflet.js"></script>
    <style>
        #map {
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
        }
    </style>
</head>
<body>
<div id="map"></div>

<script>
    function createNDimArray(dimensions) {
        if (dimensions.length > 0) {
            var dim = dimensions[0];
            var rest = dimensions.slice(1);
            var newArray = [];
            for (var i = 0; i < dim; i++) {
                newArray[i] = createNDimArray(rest);
            }
            return newArray;
        } else {
            return undefined;
        }
    }

    let wayArr = createNDimArray([0, 0, 0]);

    let k = 0;
    <c:forEach items="${ways}" var="way"><jsp:useBean id="way" type="ru.connectivitytest.site.model.SimpleWay"/>

        var way = [];
        <c:forEach items="${way.nodes}" var="node"><jsp:useBean id="node" type="ru.connectivitytest.site.model.SimpleNode"/>
            way.push([${node.lat}, ${node.lon}]);
            k++;
        </c:forEach>
        wayArr[k] = way;

    </c:forEach>
    let wayArray2 = wayArr.filter(element => element !== null || true);

    var map = L.map('map').setView([45, 45], 4);
    L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png').addTo(map);

    for (i = 0; i < wayArray2.length; i++) {
        var polyline = L.polyline(wayArray2[i], {color: 'red'}).addTo(map);
       // var marker = L.marker(wayArray2[i][0]).addTo(map);
    }
    // zoom the map to the polyline
    // map.fitBounds(polyline.getBounds());
</script>
</body>
</html>