# API Escalabilidade

Essa aplicação é dependente dos seguintes componentes externos:

- Apache Kakfa, para queueing
- Apache Spark, para streaming
- Apache Cassandra, para registro de transações
- Elasticsearch, para geração de relatórios

## Configuração (idealmente deveria ser transcrito para Ansible)

O projeto é baseado no Spring Boot, por isso as propriedades são definidas no arquivo *application.proporties*. As seguintes referências ensinam como instalar o projeto no Ubuntu:

A - Spark

http://blog.prabeeshk.com/blog/2014/10/31/install-apache-spark-on-ubuntu-14-dot-04/

B - Install Kafka

https://www.digitalocean.com/community/tutorials/how-to-install-apache-kafka-on-ubuntu-14-04
http://stackoverflow.com/questions/34259069/apache-kafka-failed-to-update-metadata-java-nio-channels-closedchannelexception

C -  Apache Cassandra

https://www.digitalocean.com/community/tutorials/how-to-install-cassandra-and-run-a-single-node-cluster-on-ubuntu-14-04

D - Elasticsearch

https://www.digitalocean.com/community/tutorials/how-to-install-and-configure-elasticsearch-on-ubuntu-14-04
https://www.elastic.co/guide/en/kibana/current/setup.html

## Iniciando

Todos componentes devem ser inciados antes de executar o projeto. Para executar esse projeto, inicie *br.com.escalabilidade.Server*

## Componente de análise

A ideia é coletar dados de pagamento para posteriormente analizar os hábitos dos clientes. O sistema - baseado na Arquitetura Lambda - tem dois fluxos principais:

1. Online flow, permite que um cliente acesse micro serviços (que não estão implementados) para acessar seu balanço,  fazer uma transferência, ou pagar uma conta. Esse fluxo registra todas suas transações no Cassandra.
2. Batch flow, que envia (de tempos em tempos) as transações para o Elasticsearch para que um analista busque por informações using Kibana.
