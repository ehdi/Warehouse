# Warehouse
The Warehouse application provides RESTful API and it has 2 endpoints, with those endpoints you can get the list of products and remove a product.

The document it available on `http://localhost:8080/docs/index.html`

## How to build in your computer
```
docker build -t warehouse:0.0.1 .
```

## How to run
```
docker run -p 8080:8080 warehouse:0.0.1
```
