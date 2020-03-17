ECHO ON
docker-compose down
docker image rm rent_service
docker-compose up -d --build
pause