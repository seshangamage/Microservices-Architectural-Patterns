
## Build Docker Image
```
docker build -f Dockerfile -t gateway:basic .
docker build -f Dockerfile_optimized -t gateway:v1 .
```

## List Docker Images
```
docker images
```

## Run Docker Container (example)
```
docker run -p 8080:8080 gateway:basic
```

