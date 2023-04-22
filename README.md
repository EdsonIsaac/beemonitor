# BeeMonitor

Este projeto foi construido utilizando Java 17 + Spring Boot 2.7.10 no backend e TypeScript + Angular 15

## Instalação do backend no Docker

Utilize o Maven para fazer o build do backend

Crie a imagem do projeto

<pre><code>docker build -t beemonitor-backend .</code></pre>

Crie a rede do projeto

<pre><code>docker network create beemonitor</code></pre>

Execute a imagem do projeto

<pre><code>docker run -p 8080:8080 --name beemonitor-backend --network beemonitor -d beemonitor-backend</code></pre>

## Instalação do frontend no Docker

Crie a imagem do projeto

<pre><code>docker build -t beemonitor-frontend .</code></pre>

Execute a imagem do projeto

<pre><code>docker run -p 80:80 --name beemonitor-frontend --network beemonitor -d beemonitor-frontend</code></pre>