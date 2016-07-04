ConsultaTSE
===========

Programa de consulta aos dados eleitorais brasileiros desde 1998.

Foi desenvolvido usando o framework MVC [VRaptor 3](http://vraptor3.vraptor.org/pt/) e em produção roda no container Tomcat 6, sendo que a principal diferença para um projeto padrão VRaptor é que as consultas são feitas direto em SQL e não Hibernate, para podermos otimizar as consultas nos dados, que são organizados em [Esquema Estrela](https://en.wikipedia.org/wiki/Star_schema). O sistema fornece como saída da consulta um CSV escrito diretamente no buffer de download para uma resposta mais rápida (classe CSVBuilder do pacote Util). 

Durante o desenvolvimento julgamos tomamos a escolha de tipar todo o sistema de tabelas e atributos. Veja, por exemplo a classe br.fgv.model.Tabela, em que definimos a classe Tabela assim como as instanciamos. Também definimos as colunas de cada tabela e criamos relacionamentos entre elas.

Outra decisão tomada nesse sentido foi criar uma linguagem de construção de Queries. Veja a classe QueryBuilder no pacote util.

A configuração dos formulários disponíveis está na classe BusinessImpl, está é acessada pela View na hora de popular os dropdowns.

== Build ==

Este sistema usa Maven para build, entretanto hoje os testes de unidade requerem que o banco de dados esteja populado, obrigando a ou rodar o ETL localmente ou carregar um dump do banco de dados.




