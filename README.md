# Image Processing Service

This is a sample Spring boot-based microservice. This contains the following microservices.

## Image Processing Service: 
* Listens to requests at image-processing:8080
* Authenticates the requests based JWT token, extracts client ID, tenant ID. 
* Stores the images in the blob store, and gets the image location
* Creates a Job object and send the job to Worker Service for image processing.  Hosts services on 8080. 
## Worker Service:
* Listens to requests at worker-service:8081 and accepts Job objects at /api/v1/job and reports status at /api/v1/job/{id}/status
## Blob Storage Service
* Listens to requests at blobl-storage:8082 and accepts Job objects at /api/v1/blob and reports status at /api/v1/blob/{id}/status

## Pre-requisites
This solution requires Docker and Docker Compose to be installed.

## Installation

Follow the below steps to install the solution.

* Clone the repo https://github.com/shivasr/image-processor.git 

```bash
git clone https://github.com/shivasr/image-processor.git
```
* Change the directory into image-processor

```bash
cd image-processor
```
* Compile and start the solution

```bash
sh ./run_solution.sh
```

### Note: If for some reason, if you cannot run the bash script follow the below steps to compile and start the services:

* Compile the application at image-processing/, worker-service/, blob-store/

* Start the docker services using docker-compose:
```bash
docker-compose up --build
```

## Usage
* Once the services are up and running you can test the API by posting JSON containing data to services listening at /. The below API return
```
curl -d ' { \
 		"encoding" : "base64",  \
		"md5" : "<MD5>", \
		"content" : "<Image Content>"\
		} ' \
	-X POST \
	-H 'Content-Type: application/json' \
	-H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJ0aWQiOjEsIm9pZCI6MSwiYXVkIjoiY29tLmNvbXBhbnkuam9ic2VydmljZSIsImF6cCI6IjEiLCJlbWFpbCI6ImN1c3RvbWVyQG1haWwuY29tIn0.CcTapGbWX0UEMovUwC8kAcWMUxmbOeO0qhsu-wqHQH0" \
	http://localhost:8080/
```

Sample Response:
```
{
    "jobStatus": "RUNNING",
    "tenantId": "1",
    "clientId": "1",
    "imageLocation": "http://blob-store/api/v1/blob/1",
    "errorMessage": null,
    "id": 1
}
```

* You can track the status by hitting the REST API http://image-processing:8080/{id}/status
## Contributing

## License
[MIT](https://choosealicense.com/licenses/mit/)
