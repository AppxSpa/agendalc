#!/bin/bash
# 1. Bajar la imagen fresca de la nube
sudo docker pull mirkogutierrezappx/agendalc:latest

# 2. Limpiar el contenedor anterior
sudo docker stop agendalc-container 2>/dev/null
sudo docker rm agendalc-container 2>/dev/null

# 3. Correr la nueva versión
sudo docker run \
    --restart always \
    -d -p 8081:8081 \
    --env-file .env \
    --network appx \
    --add-host=host.docker.internal:host-gateway \
    --name agendalc-container agendalc \
    mirkogutierrezappx/agendalc:latest