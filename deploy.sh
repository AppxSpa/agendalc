sudo docker stop agendalc-container 2>/dev/null
sudo docker rm agendalc-container 2>/dev/null

sudo docker build -t agendalc .

sudo docker run \
           --restart always \
           -d -p 8081:8081 \
           --env-file .env \
           --network appx \
	   --add-host=host.docker.internal:host-gateway \
	   --name agendalc-container agendalc
