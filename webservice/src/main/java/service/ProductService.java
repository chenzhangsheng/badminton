package service;

import java.util.List;
import java.util.Map;

import base.Product;
import com.github.pagehelper.PageInfo;



public interface ProductService {
	
	public List<Product> selectProductByParms(Product product);
	public PageInfo selectProductListByParams(Map<String, Object> map) throws Exception;
	public List<Product> selectbyClassify(Product product);
	
	public Product phoneSelectProAndOneAssess(Product product);
	
	public int saveProduct(Product Product) throws Exception;
	public boolean deleteProductIds(List<Integer> ids)throws Exception;
	public boolean updateProduct(Product Product) throws Exception;
	public Product selectOne(Product Product)throws Exception;
	
	public List<Product> findProductByParms(Map<String, Object> map);
}
