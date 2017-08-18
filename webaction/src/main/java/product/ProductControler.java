package product;

import base.BaseController;
import base.Product;
import bean.ResultBean;
import bean.Title;
import com.github.pagehelper.PageInfo;
import exception.InvalidRequestRuntimeException;
import mongo.TitleService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import query.ProductQuery;
import service.ProductService;
import utils.ST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping(value = "/product")
public class ProductControler extends BaseController {

	@Autowired
	private ProductService productService;
	@Autowired
	private TitleService titleService;

	/**
	 * 后台查询商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectProduct", method = { RequestMethod.POST })
	@ResponseBody
	public Object selectAdminByParams(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Title query = (Title) JSONObject.toBean(getPostJSONObject(request), Title.class);
//		String sidx = getParam("sidx");// 排序字段;
//		String sord = getParam("sord");// 升序降序;
//		Product product = new Product();
//		product.setId(1);
//		PageInfo pageInfo = new PageInfo();
		try {
//			int oneRecord = 10;// 一页几行
//			int pageNo = 1;// 第几页
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("sidx", sidx);// 排序字段
//			map.put("sord", sord);// 升序降序
//			map.put("rowCount", oneRecord);// 一页几行
//			map.put("pageNo", pageNo);
//			product = productService.selectOne(product);
			titleService.insert(query);

			return new ResultBean(titleService.get(query.getTitleid()),ResultBean.OK,"getProductionList success");
		} catch (Exception e) {
			log.error("selectProduct error:" + e.getMessage() + "_" + ExceptionUtils.getStackTrace(e));
			throw new InvalidRequestRuntimeException("getProductionList failure: ",
					"getProductionList failure: " + e.getMessage());
		}

	}


}
