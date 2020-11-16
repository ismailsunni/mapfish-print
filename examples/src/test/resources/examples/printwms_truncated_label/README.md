Testing truncated label.

1. Starting QGIS Server (run from this directory):

   ```bash
   docker run --rm -d -p 8380:80 --volume=$PWD/qgis-server/:/etc/qgisserver --name c2c-countries camptocamp/qgis-server
   ```