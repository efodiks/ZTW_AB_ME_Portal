#Docker instructions

###locally
docker build -t efodikss/portal-backend:latest .
docker push efodikss/portal-backend:latest


###AWS
docker pull efodikss/portal-backend
docker run --name backend -d -p 8080:8080 -e "SECURITY_SECRET=nkHdAWNrPz6iS4skz79mnwbLaxaeW9aXrwi7ijKi/GoONIk45VjnwLFn3/voMIhYvO/nBjdnUHEs/x66Pt0/BQ==" efodikss/portal-backend