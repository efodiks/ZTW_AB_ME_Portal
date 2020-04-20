# Select the image to use
FROM node

ADD build /app

RUN npm install -g serve

WORKDIR /
ENTRYPOINT ["serve","-s","app"]

EXPOSE 5000
