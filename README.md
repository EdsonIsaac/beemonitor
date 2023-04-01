# BeeMonitor

Este projeto foi construido utilizando Java 17 + Spring Boot 2.7.10 no backend e TypeScript + Angular 15

## Instalação do backend no Docker

Utilize o Maven para fazer o build do backend

Crie a rede do projeto

<pre><code>docker network create beemonitor</code></pre>

Faça o download da imagem do PostgreSQL

<pre><code>docker pull postgres</code></pre>

Execute a imagem do PostgreSQL

<pre><code>docker run -p 5432:5432 --name beemonitor-database --network beemonitor -e POSTGRES_PASSWORD=beemonitor -e POSTGRES_USER=beemonitor -e POSTGRES_DB=beemonitor -d postgres</code></pre>

Crie a imagem do projeto

<pre><code>docker build -t beemonitor-backend:latest .</code></pre>

Execute a imagem do projeto

<pre><code>docker run -p 8080:8080 --name beemonitor-backend --network beemonitor -e APP_DATASOURCE_URL=jdbc:postgresql://beemonitor-database:5432/beemonitor -e APP_DATASOURCE_USERNAME=beemonitor -e APP_DATASOURCE_PASSWORD=beemonitor -d beemonitor-backend:latest</code></pre>

## Instalação do frontend no Docker

Crie a imagem do projeto

<pre><code>docker build -t beemonitor-frontend:latest .</code></pre>

Execute a imagem do projeto

<pre><code>docker run -p 80:80 --name beemonitor-frontend --network beemonitor -d beemonitor-frontend:latest</code></pre>
