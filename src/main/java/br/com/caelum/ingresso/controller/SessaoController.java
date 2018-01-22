package br.com.caelum.ingresso.controller;

import javax.validation.Valid;

import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.form.SessaoForm;

@Controller
public class SessaoController {
	
	@Autowired
	private SalaDao sDao;
	
	@Autowired
	private FilmeDao fDao;
	
	@Autowired
	private SessaoDao sessaoDao;
	
	@PostMapping(value = "/admin/sessao")
	@Transactional
	public ModelAndView salva(@Valid SessaoForm form, BindingResult result) {
		if (result.hasErrors())
				return form(form.getSalaId(), form);
		
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/sala/"+form.getSalaId()+"/sessoes");
		
		Sessao sessao = form.toSessao(sDao, fDao);
		sessaoDao.save(sessao);
		
		return modelAndView;
	}
	
	@GetMapping("/admin/sessao")
	public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm form) {
		
		ModelAndView mEv = new ModelAndView("/admin/sessao");
		mEv.addObject("sala", sDao.findOne(salaId));
		mEv.addObject("filmes", sDao.findAll());
		mEv.addObject("form", form);
		return mEv; 
	}

}
