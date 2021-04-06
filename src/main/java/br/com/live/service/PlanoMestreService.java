package br.com.live.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.bo.CalculoDistribuicaoPecas;
import br.com.live.bo.GeracaoPlanoMestre;
import br.com.live.bo.GeracaoPreOrdens;
import br.com.live.bo.Multiplicador;
import br.com.live.custom.DemandaProdutoCustom;
import br.com.live.custom.EstoqueProdutoCustom;
import br.com.live.custom.OcupacaoPlanoMestreCustom;
import br.com.live.custom.PlanoMestreCustom;
import br.com.live.custom.PrevisaoVendasCustom;
import br.com.live.custom.ProcessoProdutoCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.entity.DemandaProdutoPlano;
import br.com.live.entity.EstoqueProduto;
import br.com.live.entity.PlanoMestre;
import br.com.live.entity.PlanoMestreConsultaItens;
import br.com.live.entity.PlanoMestreConsultaTamanhos;
import br.com.live.entity.PlanoMestreParamProgItem;
import br.com.live.entity.PlanoMestreParametros;
import br.com.live.entity.PlanoMestrePreOrdem;
import br.com.live.entity.PlanoMestrePreOrdemItem;
import br.com.live.entity.ProcessoProdutoPlano;
import br.com.live.entity.ProdutoPlanoMestrePorCor;
import br.com.live.model.AlternativaRoteiroPadrao;
import br.com.live.model.ConsultaPreOrdemProducao;
import br.com.live.model.MarcacaoRisco;
import br.com.live.model.OcupacaoPlanoPorArtigo;
import br.com.live.model.OcupacaoPlanoPorEstagio;
import br.com.live.model.ProgramacaoPlanoMestre;
import br.com.live.entity.ProdutoPlanoMestre;
import br.com.live.repository.PlanoMestreConsultaItensRepository;
import br.com.live.repository.PlanoMestreConsultaTamanhosRepository;
import br.com.live.repository.PlanoMestreParamProgItemRepository;
import br.com.live.repository.PlanoMestreParametrosRepository;
import br.com.live.repository.PlanoMestrePreOrdemItemRepository;
import br.com.live.repository.PlanoMestrePreOrdemRepository;
import br.com.live.repository.PlanoMestreRepository;
import br.com.live.repository.ProdutoPlanoMestrePorCorRepository;
import br.com.live.repository.ProdutoPlanoMestreRepository;
import br.com.live.util.ParametrosPlanoMestre;

@Service
@Transactional
public class PlanoMestreService {

	private final PlanoMestreRepository planoMestreRepository;
	private final ProdutoPlanoMestreRepository produtoPlanoMestreRepository;
	private final EstoqueProdutoCustom estoqueProdutoRepository;
	private final DemandaProdutoCustom demandaProdutoRepository;
	private final ProcessoProdutoCustom processoProdutoRepository;
	private final PlanoMestreParametrosRepository planoMestreParametrosRepository;
	private final ProdutoPlanoMestrePorCorRepository produtoPlanoMestrePorCorRepository;
	private final PlanoMestreConsultaItensRepository planoMestreConsultaItensRepository;
	private final PlanoMestreConsultaTamanhosRepository planoMestreConsultaTamanhosRepository;
	private final ProdutoCustom produtoRepository;
	private final PlanoMestreParamProgItemRepository planoMestreParamProgItemRepository;
	private final OcupacaoPlanoMestreCustom ocupacaoPlanoMestreRepository;
	private final PlanoMestreCustom planoMestreCustom;
	private final PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository; 
	private final PlanoMestrePreOrdemItemRepository planoMestrePreOrdemItemRepository;
	private final PrevisaoVendasCustom previsaoVendasCustom;

	public PlanoMestreService(PlanoMestreRepository planoMestreRepository,
			ProdutoPlanoMestreRepository produtoPlanoMestreRepository, EstoqueProdutoCustom estoqueProdutoRepository,
			DemandaProdutoCustom demandaProdutoRepository, ProcessoProdutoCustom processoProdutoRepository,
			PlanoMestreParametrosRepository planoMestreParametrosRepository,
			ProdutoPlanoMestrePorCorRepository produtoPlanoMestrePorCorRepository,
			PlanoMestreConsultaItensRepository planoMestreConsultaItensRepository,
			PlanoMestreConsultaTamanhosRepository planoMestreConsultaTamanhosRepository,
			ProdutoCustom produtoRepository, PlanoMestreParamProgItemRepository planoMestreParamProgItemRepository,
			OcupacaoPlanoMestreCustom ocupacaoPlanoMestreRepository, PlanoMestreCustom planoMestreCustom,
			PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository,
			PlanoMestrePreOrdemItemRepository planoMestrePreOrdemItemRepository,
			PrevisaoVendasCustom previsaoVendasCustom) {
		this.planoMestreRepository = planoMestreRepository;
		this.produtoPlanoMestreRepository = produtoPlanoMestreRepository;
		this.estoqueProdutoRepository = estoqueProdutoRepository;
		this.demandaProdutoRepository = demandaProdutoRepository;
		this.processoProdutoRepository = processoProdutoRepository;
		this.planoMestreParametrosRepository = planoMestreParametrosRepository;
		this.produtoPlanoMestrePorCorRepository = produtoPlanoMestrePorCorRepository;
		this.planoMestreConsultaItensRepository = planoMestreConsultaItensRepository;
		this.planoMestreConsultaTamanhosRepository = planoMestreConsultaTamanhosRepository;
		this.produtoRepository = produtoRepository;
		this.planoMestreParamProgItemRepository = planoMestreParamProgItemRepository;
		this.ocupacaoPlanoMestreRepository = ocupacaoPlanoMestreRepository;
		this.planoMestreCustom = planoMestreCustom;
		this.planoMestrePreOrdemRepository = planoMestrePreOrdemRepository;
		this.planoMestrePreOrdemItemRepository = planoMestrePreOrdemItemRepository;
		this.previsaoVendasCustom = previsaoVendasCustom;
	}

	public List<PlanoMestre> findAll() {
		return planoMestreRepository.findAll();
	}

	public List<PlanoMestreConsultaItens> findProdutos(long idPlanoMestre) {
		return planoMestreConsultaItensRepository.findByIdPlanoMestre(idPlanoMestre);
	}

	public List<PlanoMestreConsultaTamanhos> findTamanhos(long idPlanoMestre, String grupo, String item) {
		return planoMestreConsultaTamanhosRepository.findByIdPlanoGrupoItem(idPlanoMestre, grupo, item);
	}

	public PlanoMestreParametros findParametros(long idPlanoMestre) {
		return planoMestreParametrosRepository.findByIdPlanoMestre(idPlanoMestre);
	}

	public PlanoMestreParamProgItem findParamProgItem(long idPlanoMestre, String grupo, String item) {
		ProdutoPlanoMestrePorCor produtoPlanoMestrePorCor = produtoPlanoMestrePorCorRepository
				.findByCodigo(idPlanoMestre, grupo, item);
		return planoMestreParamProgItemRepository.findByIdItemPlanoMestre(produtoPlanoMestrePorCor.id);
	}

	public OcupacaoPlanoPorEstagio findOcupacaoEstagio(long idPlanoMestre, int estagio) {
		return ocupacaoPlanoMestreRepository.findOcupacaoByEstagio(idPlanoMestre, estagio);
	}

	public List<OcupacaoPlanoPorArtigo> findOcupacaoArtigo(long idPlanoMestre, int estagio) {
		return ocupacaoPlanoMestreRepository.findOcupacaoArtigosByEstagio(idPlanoMestre, estagio);
	}

	public List<ConsultaPreOrdemProducao> findPreOrdensByIdPlanoMestre(long idPlanoMestre) {
		return planoMestreCustom.findPreOrdensByIdPlanoMestre(idPlanoMestre);
	}
	
	public void salvarSituacao(long idPlanoMestre, int newSituacao) {		
		PlanoMestre planoMestre = planoMestreRepository.findById(idPlanoMestre);		
		planoMestre.situacao = newSituacao;		
		planoMestreRepository.save(planoMestre);		
	}
	
	public void salvarItens(List<PlanoMestreConsultaItens> itensAlterados) {

		for (PlanoMestreConsultaItens itemAlterado : itensAlterados) {
			ProdutoPlanoMestrePorCor produtoCor = produtoPlanoMestrePorCorRepository
					.findByCodigo(itemAlterado.idPlanoMestre, itemAlterado.grupo, itemAlterado.item);

			if (produtoCor.qtdeProgramada != itemAlterado.qtdeProgramada) {

				PlanoMestreParametros parametros = planoMestreParametrosRepository
						.findByIdPlanoMestre(itemAlterado.idPlanoMestre);

				produtoCor.qtdeProgramada = itemAlterado.qtdeProgramada;
				produtoPlanoMestrePorCorRepository.save(produtoCor);

				List<ProdutoPlanoMestre> produtos = null;

				if (parametros.tipoDistribuicao == 1)
					produtos = calcularGradePadrao(produtoCor);

				if (parametros.tipoDistribuicao == 2)
					produtos = calcularGradeVenda(produtoCor);

				if (parametros.tipoDistribuicao == 3)
					produtos = calcularGradeNegativa(produtoCor);

				if (parametros.tipoDistribuicao == 4)
					produtos = calcularGradePadrao(produtoCor);
				
				produtoPlanoMestreRepository.saveAll(produtos);

				aplicarMultiplicador(produtoCor.idPlanoMestre, produtoCor.grupo, produtoCor.item);
			}
		}
	}

	public void salvarParametrosProgramacaoItem(long idPlanoMestre, String grupo, String item, int alternativa, int roteiro, int periodo, int multiplicador) {		
		ProdutoPlanoMestrePorCor produtoPlanoMestrePorCor = produtoPlanoMestrePorCorRepository.findByCodigo(idPlanoMestre, grupo, item);
		PlanoMestreParamProgItem planoMestreParamProgItem = planoMestreParamProgItemRepository.findByIdItemPlanoMestre(produtoPlanoMestrePorCor.id);
		
		planoMestreParamProgItem.alternativa = alternativa;
		planoMestreParamProgItem.roteiro = roteiro;
		planoMestreParamProgItem.periodo = periodo;
		planoMestreParamProgItem.multiplicador = multiplicador;		
		planoMestreParamProgItemRepository.save(planoMestreParamProgItem);
		
		aplicarMultiplicadorItem(idPlanoMestre, multiplicador, produtoPlanoMestrePorCor);				
	}
	
	public void salvarGrade(long idPlanoMestre, String grupo, String item,
			List<PlanoMestreConsultaTamanhos> gradeAlterada) {

		for (PlanoMestreConsultaTamanhos itemAlterado : gradeAlterada) {
			ProdutoPlanoMestre produto = produtoPlanoMestreRepository.findByIdPlanoCodGrupoSubCor(
					itemAlterado.idPlanoMestre, itemAlterado.grupo, itemAlterado.sub, itemAlterado.item);
			produto.qtdeProgramada = itemAlterado.qtdeProgramada;
			produtoPlanoMestreRepository.save(produto);
		}

		aplicarMultiplicador(idPlanoMestre, grupo, item);
	}

	public List<PlanoMestre> gerar(ParametrosPlanoMestre parametros) {

		long idPlanoMestre = 0;

		List<EstoqueProduto> estoques = estoqueProdutoRepository.findByParameters(parametros);
		List<DemandaProdutoPlano> demandas = demandaProdutoRepository.findByParameters(parametros);
		List<ProcessoProdutoPlano> processos = processoProdutoRepository.findByParameters(parametros);
		
		GeracaoPlanoMestre geracao = new GeracaoPlanoMestre(parametros, estoques, demandas, processos);

		idPlanoMestre = gravar(geracao);
		equalizarDistribuicao(idPlanoMestre, geracao.getParametrosPlanoMestre().tipoDistribuicao);

		return findAll();
	}

	private long gravar(GeracaoPlanoMestre geracao) {

		PlanoMestre planoMestre = geracao.getCapaPlanoMestre();
		List<ProdutoPlanoMestre> produtos = geracao.getProdutosPlanoMestre();

		planoMestreRepository.save(planoMestre);

		PlanoMestreParametros planoMestreParametros = geracao.getParametrosPlanoMestre();
		planoMestreParametros.idPlanoMestre = planoMestre.id;
		planoMestreParametrosRepository.save(planoMestreParametros);

		for (ProdutoPlanoMestre produtoPlanoMestre : produtos) {
			produtoPlanoMestre.idPlanoMestre = planoMestre.id;
			produtoPlanoMestre.qtdePrevisao = previsaoVendasCustom.findPrevisaoVendaByProduto(planoMestreParametros.colecoes, produtoPlanoMestre.grupo, produtoPlanoMestre.item);
			produtoPlanoMestreRepository.save(produtoPlanoMestre);
		}

		List<ProdutoPlanoMestrePorCor> produtosPorCor = geracao.getProdutosPorCorPlanoMestre(produtos);
		
		for (ProdutoPlanoMestrePorCor produtoPlanoMestrePorCor : produtosPorCor) {
			produtoPlanoMestrePorCor.idPlanoMestre = planoMestre.id;
			produtoPlanoMestrePorCorRepository.save(produtoPlanoMestrePorCor);
			AlternativaRoteiroPadrao alternativaRoteiroPadrao = produtoRepository.findAlternativaRoteiroPadraoByCodigo(
					produtoPlanoMestrePorCor.grupo, produtoPlanoMestrePorCor.item);
			PlanoMestreParamProgItem parametroProgramacaoItem = geracao.getParametrosProgramacaoItem(planoMestre.id,
					produtoPlanoMestrePorCor.id, alternativaRoteiroPadrao);
			planoMestreParamProgItemRepository.save(parametroProgramacaoItem);
		}

		return (planoMestre.id);
	}

	private void equalizarDistribuicao(long idPlanoMestre, int tipoDistribuicao) {
		if (tipoDistribuicao == 1) // Grade Padrão
			calcularGradePadraoParaPlano(idPlanoMestre);

		if (tipoDistribuicao == 2) // Grade Venda
			calcularGradeVendaParaPlano(idPlanoMestre);

		if (tipoDistribuicao == 3) // Grade Negativa
			calcularGradeNegativaParaPlano(idPlanoMestre);
		
		if (tipoDistribuicao == 4) // Previsão de Vendas
			calcularGradePrevisaoParaPlano(idPlanoMestre); 
	}

	private void calcularGradePadraoParaPlano(long idPlanoMestre) {

		PlanoMestreParametros parametros = planoMestreParametrosRepository.findByIdPlanoMestre(idPlanoMestre);

		List<ProdutoPlanoMestrePorCor> produtosCor = produtoPlanoMestrePorCorRepository
				.findByIdPlanoMestre(idPlanoMestre);

		for (ProdutoPlanoMestrePorCor produtoCor : produtosCor) {
			List<ProdutoPlanoMestre> produtos = calcularGradePadrao(produtoCor);
			produtoPlanoMestreRepository.saveAll(produtos);

			aplicarMultiplicadorItem(idPlanoMestre, parametros.multiplicador, produtoCor);
		}
	}

	private List<ProdutoPlanoMestre> calcularGradePadrao(ProdutoPlanoMestrePorCor produtoCor) {
		PlanoMestreParamProgItem parametrosProgramacao = planoMestreParamProgItemRepository
				.findByIdItemPlanoMestre(produtoCor.id);

		int riscoPadrao = produtoRepository.findRiscoPadraoByCodigo(produtoCor.grupo);
		int seqRisco = produtoRepository.findSequenciaPrincipalRisco(produtoCor.grupo, produtoCor.item,
				parametrosProgramacao.alternativa);

		List<MarcacaoRisco> marcacoesRisco = produtoRepository.findMarcacoesRisco(produtoCor.grupo, riscoPadrao,
				seqRisco, parametrosProgramacao.alternativa);

		List<ProdutoPlanoMestre> produtos = produtoPlanoMestreRepository
				.findByIdPlanoCodGrupoCor(produtoCor.idPlanoMestre, produtoCor.grupo, produtoCor.item);

		return CalculoDistribuicaoPecas.distribuirPelaGradePadrao(produtoCor.qtdeProgramada, produtos, marcacoesRisco);
	}

	private void calcularGradeVendaParaPlano(long idPlanoMestre) {

		PlanoMestreParametros parametros = planoMestreParametrosRepository.findByIdPlanoMestre(idPlanoMestre);

		List<ProdutoPlanoMestrePorCor> produtosCor = produtoPlanoMestrePorCorRepository
				.findByIdPlanoMestre(idPlanoMestre);

		for (ProdutoPlanoMestrePorCor produtoCor : produtosCor) {
			List<ProdutoPlanoMestre> produtos = calcularGradePadrao(produtoCor);
			produtoPlanoMestreRepository.saveAll(produtos);

			aplicarMultiplicadorItem(idPlanoMestre, parametros.multiplicador, produtoCor);
		}
	}

	private List<ProdutoPlanoMestre> calcularGradeVenda(ProdutoPlanoMestrePorCor produtoCor) {

		List<ProdutoPlanoMestre> produtos = produtoPlanoMestreRepository
				.findByIdPlanoCodGrupoCor(produtoCor.idPlanoMestre, produtoCor.grupo, produtoCor.item);

		return CalculoDistribuicaoPecas.distribuirPelaGradeVenda(produtoCor.qtdeProgramada, produtos);
	}

	private void calcularGradeNegativaParaPlano(long idPlanoMestre) {

		PlanoMestreParametros parametros = planoMestreParametrosRepository.findByIdPlanoMestre(idPlanoMestre);

		List<ProdutoPlanoMestrePorCor> produtosCor = produtoPlanoMestrePorCorRepository
				.findByIdPlanoMestre(idPlanoMestre);

		for (ProdutoPlanoMestrePorCor produtoCor : produtosCor) {
			aplicarMultiplicadorItem(idPlanoMestre, parametros.multiplicador, produtoCor);
		}
	}

	private List<ProdutoPlanoMestre> calcularGradeNegativa(ProdutoPlanoMestrePorCor produtoCor) {

		List<ProdutoPlanoMestre> produtos = produtoPlanoMestreRepository
				.findByIdPlanoCodGrupoCor(produtoCor.idPlanoMestre, produtoCor.grupo, produtoCor.item);

		return CalculoDistribuicaoPecas.distribuirPelaQtdeSugerida(produtoCor.qtdeProgramada, produtos);
	}
	
	private void calcularGradePrevisaoParaPlano(long idPlanoMestre) {
		calcularGradePadraoParaPlano(idPlanoMestre);
	}
	
	private void aplicarMultiplicadorItem(long idPlanoMestre, int multiplicador, ProdutoPlanoMestrePorCor produtoCor) {

		int qtdeProgramada = 0;

		List<ProdutoPlanoMestre> produtos = produtoPlanoMestreRepository.findByIdPlanoCodGrupoCor(idPlanoMestre,
				produtoCor.grupo, produtoCor.item);

		for (ProdutoPlanoMestre produto : produtos) {
			produto.qtdeEqualizadoSugestao = Multiplicador.ajustarQuantidade(multiplicador, produto.qtdeProgramada);
			produto.qtdeDiferencaSugestao = produto.qtdeSugestao - produto.qtdeEqualizadoSugestao;
			produto.qtdeProgramada = produto.qtdeEqualizadoSugestao;
			produtoPlanoMestreRepository.save(produto);
			qtdeProgramada += produto.qtdeProgramada;
		}

		produtoCor.qtdeEqualizadoSugestao = qtdeProgramada;
		produtoCor.qtdeDiferencaSugestao = produtoCor.qtdeSugestao - produtoCor.qtdeEqualizadoSugestao;
		produtoCor.qtdeProgramada = produtoCor.qtdeEqualizadoSugestao;

		produtoPlanoMestrePorCorRepository.save(produtoCor);

	}

	public void aplicarMultiplicador(long idPlanoMestre, String grupo, String item) {

		ProdutoPlanoMestrePorCor produtoPorCor = produtoPlanoMestrePorCorRepository.findByCodigo(idPlanoMestre, grupo,
				item);

		PlanoMestreParamProgItem parametrosProgramacao = planoMestreParamProgItemRepository
				.findByIdItemPlanoMestre(produtoPorCor.id);

		int multiplicador = parametrosProgramacao.multiplicador;

		if (multiplicador <= 0)
			multiplicador = 1;

		aplicarMultiplicadorItem(idPlanoMestre, multiplicador, produtoPorCor);
	}

	public void gerarPreOrdens(ParametrosPlanoMestre parametros) {

		// Atualiza os parâmetros do plano mestre.
		PlanoMestreParametros planoMestreParametros = planoMestreParametrosRepository.findByIdPlanoMestre(parametros.idPlanoMestre);		
		planoMestreParametros.agrupaOpPorRefer = parametros.agrupaOpPorRefer;
		planoMestreParametros.qtdeMaximaOP = parametros.qtdeMaximaOP;
		planoMestreParametros.qtdeMinimaOP = parametros.qtdeMinimaOP;
		planoMestreParametros.periodoOP =  parametros.periodoOP;
		planoMestreParametros.depositoOP = parametros.depositoOP;
		planoMestreParametros.observacaoOP = parametros.observacaoOP;		
		planoMestreParametrosRepository.save(planoMestreParametros);		
		
		// Elimina as pré-ordens geradas anteriormente para o plano mestre.		
		planoMestrePreOrdemItemRepository.deleteByIdPlanoMestre(parametros.idPlanoMestre);
		planoMestrePreOrdemRepository.deleteByIdPlanoMestre(parametros.idPlanoMestre);
		
		// Calcula e gera as pré-ordens		
		List<ProgramacaoPlanoMestre> programacao = planoMestreCustom
				.findProgramacaoIdByPlanoMestre(parametros.idPlanoMestre);

		GeracaoPreOrdens geracaoPreOrdens = new GeracaoPreOrdens(parametros.idPlanoMestre, parametros.agrupaOpPorRefer,
				parametros.qtdeMaximaOP, parametros.qtdeMinimaOP, parametros.periodoOP, parametros.depositoOP, parametros.observacaoOP, programacao);
		
		Map<Integer, PlanoMestrePreOrdem> mapPreOrdens = geracaoPreOrdens.getMapPreOrdens(); 
		List<PlanoMestrePreOrdemItem> listPreOrdemItens ; 
		
		PlanoMestrePreOrdem preOrdem;
		
		int idPreOrdem = planoMestreCustom.findMaxIdPreOrdem();
		int idPreOrdemItem = planoMestreCustom.findMaxIdPreOrdemItem();
		
		for (Integer idMap : mapPreOrdens.keySet()) {
			
			idPreOrdem++;
			
			preOrdem = mapPreOrdens.get(idMap);
			preOrdem.id = idPreOrdem; 
			preOrdem = planoMestrePreOrdemRepository.save(preOrdem);

			listPreOrdemItens = geracaoPreOrdens.getListPreOrdemItens(idMap);
			
			for (PlanoMestrePreOrdemItem preOrdemItem : listPreOrdemItens) {
				
				idPreOrdemItem++;
				
				preOrdemItem.id = idPreOrdemItem; 
				preOrdemItem.idOrdem = idPreOrdem;
				planoMestrePreOrdemItemRepository.save(preOrdemItem);				
			}
		}
	}

	public List<PlanoMestre> delete(long idPlanoMestre) {

		planoMestrePreOrdemItemRepository.deleteByIdPlanoMestre(idPlanoMestre);
		planoMestrePreOrdemRepository.deleteByIdPlanoMestre(idPlanoMestre);
		planoMestreParamProgItemRepository.deleteByIdPlanoMestre(idPlanoMestre);
		produtoPlanoMestrePorCorRepository.deleteByIdPlanoMestre(idPlanoMestre);
		produtoPlanoMestreRepository.deleteByIdPlanoMestre(idPlanoMestre);
		planoMestreParametrosRepository.deleteById(idPlanoMestre);
		planoMestreRepository.deleteById(idPlanoMestre);

		return findAll();
	}
};