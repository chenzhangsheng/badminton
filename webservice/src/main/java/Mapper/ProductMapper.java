package Mapper;

import java.util.List;
import java.util.Map;

import base.Product;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ProductMapper extends Mapper<Product>,MySqlMapper<Product>{
	
	/*public List<Product> selectProductByClassifyId(Integer classifyId);*/
	
	
	List<Product> selectProductAndImage(Product product);
	List<Product> selectProductByParams(Map<String, Object> map);
	void deleteByClassifyKey1(Map<String, Object> map);
	
	void deleteProductByIds(List<Integer> ids);
}