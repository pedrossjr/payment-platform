# Plataforma de Pagamentos e Gerenciamento de Contas Bancárias

## Descrição do Projeto
Este projeto é uma plataforma simples de pagamento e gerenciamento de conta bancária. O grande diferencial desta aplicação é a sua arquitetura, que foi desenhada utilizando tecnologias e padrões modernos adotados por grandes empresas no mercado de tecnologia.

## Funcionalidades
Apesar de ter um escopo funcional direcionado a pagamentos e contas bancárias, a plataforma contempla as seguintes funcionalidades focadas em resiliência e boas práticas:
- **Gerenciamento de Conta Bancária:** Operações básicas para o ciclo de vida de uma conta.
- **Plataforma de Pagamento:** Processamento e registro das transações financeiras.
- **Autenticação com JWT:** Controle de acesso seguro, garantindo que as operações sejam feitas apenas por usuários autenticados.
- **Idempotência:** Proteção contra falhas de rede e retries, garantindo que pagamentos não sejam registrados de forma duplicada.
- **Envio de E-mail:** Notificações disparadas de forma assíncrona para manter as partes informadas sobre o andamento das operações.

## Tecnologias Utilizadas
O projeto adota uma arquitetura distribuída e utiliza as seguintes tecnologias:
- **Arquitetura de Microserviços:** Serviços separados por domínio (ex: account-service, payment-service, auth-service, notification-service, service-discovery).
- **Apache Kafka:** Utilizado para comunicação assíncrona entre os serviços, garantindo alta performance e resiliência (event-driven).
- **Discovery Service com Eureka:** Registro e descoberta dinâmica dos microserviços.
- **API Gateway:** Ponto central unificado de entrada para o roteamento das requisições REST da plataforma.
- **API Rest:** Interfaces síncronas de comunicação definidas.
- **Banco de Dados Postgres:** Persistência de dados consistente e segura.
- **Docker:** Containerização dos serviços individuais e dependências para garantir consistência entre ambientes.
- **Kubernetes (K8s):** Orquestração dos containers para gerenciar escalabilidade, disponibilidade e deploy.

## Links Úteis (Ambiente Local)
Ao executar a infraestrutura localmente, você pode acessar os seguintes painéis:
- **PgAdmin (Banco de Dados):** http://localhost:5050/browser
- **Redpanda (Console Kafka):** http://localhost:8084/overview
- **Eureka Server (Service Discovery):** http://localhost:8761

---

**Propósito Educacional**  
Este é um projeto aberto para que outros desenvolvedores possam utilizá-lo como material de aprendizado, servindo como uma ótima referência prática para entender como que funciona a utilização destas tecnologias integradas. O projeto não está concluído e mais funcionalidades serão acrescentadas com o tempo, evoluindo o escopo do projeto. Como é um projeto para fins educacionais, erros talvez podem serem encontrados no decorrer do desenvolvimento.