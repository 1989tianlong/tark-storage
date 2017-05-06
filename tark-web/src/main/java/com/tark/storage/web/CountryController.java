package com.tark.storage.web;

import com.github.pagehelper.PageInfo;
import com.tark.storage.common.web.core.exception.BusinessException;
import com.tark.storage.model.Country;
import com.tark.storage.service.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by jh on 2017/5/2.
 */
@Api(value = "Mybatis 使用试例",description="一个简单的 Mybatis 使用试例")
@Controller
@RequestMapping("/countries")
public class CountryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CountryService countryService;

    @ApiOperation("获取所有国家")
    @GetMapping
    public ModelAndView getAll(Country country) {
        logger.debug("get all contries...............................");
        ModelAndView result = new ModelAndView("index");
        List<Country> countryList = countryService.getAll(country);
        result.addObject("pageInfo", new PageInfo<Country>(countryList));
        result.addObject("queryParam", country);
        result.addObject("page", country.getPage());
        result.addObject("rows", country.getRows());

        return result;
    }

    @ApiOperation("添加国家")
    @PostMapping(value = "/add")
    public ModelAndView add() {
        ModelAndView result = new ModelAndView("view");
        result.addObject("country", new Country());
        return result;
    }

    @ApiOperation("查看国家")
    @GetMapping(value = "/view/{id}")
    public ModelAndView view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView("view");
        Country country = countryService.getById(id);
        result.addObject("country", country);
        return result;
    }

    @ApiOperation("删除国家")
    @DeleteMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id, RedirectAttributes ra) {
        ModelAndView result = new ModelAndView("redirect:/countries");
        countryService.deleteById(id);
        ra.addFlashAttribute("msg", "删除成功!");
        return result;
    }

    @ApiOperation("更新国家")
    @PostMapping(value = "/save")
    public ModelAndView save(Country country) {
        ModelAndView result = new ModelAndView("view");
        String msg = country.getId() == null ? "新增成功!" : "更新成功!";
        countryService.save(country);
        result.addObject("country", country);
        result.addObject("msg", msg);
        return result;
    }
}
