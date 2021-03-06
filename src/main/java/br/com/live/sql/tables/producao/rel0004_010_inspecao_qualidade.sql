create table orion_050
(id_inspecao number(9),
 tipo number(1) default 0,
 data date,
 turno number(1) default 1,
 usuario varchar2(100) default '',
 cod_estagio number(2) default 0, 
 periodo number(4) default 0,
 ordem_producao number(9) default 0,
 ordem_confeccao number(5) default 0,
 perc_inspecionar_pcs number(3) default 0,
 qtde_inspecionar_pcs number(6) default 0,
 qtde_inspecionada_pcs number(6) default 0,
 qtde_rejeitada_pcs number(6) default 0,  
 perc_rejeitada_pcs number(3) default 0,
 constraint orion_050_pk primary key (id_inspecao));

comment on column orion_050.tipo is '0 - Pecas, 1 - Medidas';
comment on table orion_050 is 'Tabela inspeção de qualidade - pecas / paineis';
---------------------------------------------------------------------------------------------------------
create table orion_051
(id_lancamento number(9),
 id_inspecao number(9),
 cod_motivo number(3),
 quantidade number(6),
 usuario varchar2(100),
 data_hora date,
 constraint orion_051_pk primary key (id_lancamento),
 constraint fk_orion_051_orion_050  foreign key (id_inspecao) references orion_050 (id_inspecao));

comment on table orion_051 is 'Tabela inspeção de qualidade - pecas / paineis - lançamentos';
---------------------------------------------------------------------------------------------------------
create table orion_052
(id number(9),
 id_lancamento number(9),
 id_inspecao number(9),
 tipo_medida number(2),
 sequencia number(3),	
 descricao varchar2(70),
 medida_padrao number(7,3),
 toler_maxima number(6,2),
 toler_minima number(6,2),
 medida_real number(7,3), 
 variacao number(7,3),
 usuario varchar2(100) default '',
 data_hora date,	 
 constraint orion_052_pk primary key (id),
 constraint fk_orion_052_orion_050  foreign key (id_inspecao) references orion_050 (id_inspecao));
 
comment on table orion_052 is 'Tabela inspeção de qualidade - medidas - lançamentos';

---------------------------------------------------------------------------------------------------------
insert into orion_002 values (10, 'Inspeção de Qualidade', 'Confecção', '/inspecao-qualidade', '');

---------------------------------------------------------------------------------------------------------

alter table orion_050
add tipo_inspecao number(1) default 0;

---------------------------------------------------------------------------------------------------------

-- Atualizar ambiente oficial
alter table orion_050
add status number(1) default 0;

alter table orion_050
add (status_obs varchar2(1000) default '',
     usuario_liberacao varchar2(50) default '');

alter table orion_001 
add libera_inspecao_qualid number(1) default 0;