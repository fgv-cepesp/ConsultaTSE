select count(*) from (SELECT ${ano} AS 'anoEleicao',
       turno AS 'turno',
       aux_candidatos_${ano}.titulo,
       aux_candidatos_${ano}.nr_votavel,
       aux_candidatos_${ano}.resultado_des,
	   aux_candidatos_${ano}.resultado_cod,
       aux_municipio.sigla_UF,
       aux_municipio.ibge,
       voto_nominal,
	   cod_mun
FROM
  (SELECT turno,
          cod_mun,
          candidato_sk,
          sum(if(tipo_votavel = 1, qnt_votos, 0)) AS voto_nominal
   FROM voto_mun_${ano}
   WHERE cod_cargo = ${cargo} AND cod_mun='${ue}'
   GROUP BY cod_mun,
            candidato_sk,
            turno
   ORDER BY cod_mun,
            candidato_sk,
            turno) AS r
LEFT JOIN aux_candidatos_${ano} ON aux_candidatos_${ano}.surrogatekey = r.candidato_sk
LEFT JOIN aux_municipio ON aux_municipio.cod = r.cod_mun
where ${res}) as busca;