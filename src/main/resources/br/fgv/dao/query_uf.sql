select sigla_estado, cargo_cod, count(*) from (SELECT ${ano}  AS "anoEleicao",
       turno AS "turno",
       aux_candidatos_${ano}.cargo_cod,
       aux_candidatos_${ano}.sit_candidatura_cod,
       aux_candidatos_${ano}.resultado_cod,
       aux_candidatos_${ano}.resultado_des,
       aux_estados.nome_estado,
       aux_estados.ibge,
       aux_estados.sigla_estado,
       cod_cargo,
       voto_nominal
FROM   (SELECT turno,
               uf,
               candidato_sk,
	           cod_cargo,
               Sum(IF(tipo_votavel = 1, qnt_votos, 0)) AS voto_nominal
        FROM   voto_mun_${ano}
        GROUP  BY uf,
                  candidato_sk,
                  turno
        ORDER  BY uf,
                  candidato_sk,
                  turno) AS r
       LEFT JOIN aux_candidatos_${ano}
              ON aux_candidatos_${ano}.surrogatekey = r.candidato_sk
       LEFT JOIN aux_estados
              ON aux_estados.id = r.uf
	   where ${res}) as busca
 group by sigla_estado, cod_cargo;