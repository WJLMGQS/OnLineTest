package org.wjlmgqs.service.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.wjlmgqs.daomain.GeneView;
import org.wjlmgqs.daomain.IChromosome;
import org.wjlmgqs.daomain.QuestionGeneViewLexicon;
import org.wjlmgqs.web.util.WebLogger;

public class BulidBestGeneViewUtil{
	
	/**
	 * @See questionGeneViewLexicon 保存有所有试题基因和当前需要评估的染色体参数信息,不同体型使用时每次都需要重新设置
	 */
	private QuestionGeneViewLexicon questionGeneViewLexicon;
	/**
	 * @See 保存评估后的个染色体实例的适应度
	 */
	private HashMap<String,Object> bestIchromosome = new HashMap<String, Object>();//<染色体,评估参数>
	/**
	 *	@See 适应度函数，决定当前染色体实例的适应度
	 *	@param chromosome 当前试题类型染色体实例
	 */
	public void evaluate(IChromosome chromosome) {
		bestIchromosome.clear();	//必须每次都要清空
		int evaluateR = 0;	//评估参数
		//int baseLength = this.questionGeneViewLexicon.getGeneViewNumber();
		int baseDifficulty = this.questionGeneViewLexicon.getGeneViewDifficulty().getIndex();
		int diffSize = 0;		//得到当前试题类型染色体实例与构建参数的难度差距，绝对值越小越好
		int difficulty = 0;		//得到当前试题类型染色体实例的难度数值
		int length = 0;			//得到当前试题类型染色体实例的计分单位数值
		List<GeneView> genes =  chromosome.getGeneViews();	//当前试题类型染色体实例的所有基因
		for(GeneView geneView : genes){
			length = length + geneView.getLength();
			difficulty = difficulty + geneView.getDifficulty().getIndex()*length;//难度*长度
		}
		/**
		 * 得到评估参数：evaluateR ，值越大，代表当前解越优
		 */
		diffSize = Math.abs(difficulty - (length * baseDifficulty));
		evaluateR = Integer.MAX_VALUE - diffSize;//计算适应度，值越大，代表当前解越优
		/**
		 * 根据评估参数决定当前染色体是否为最高染色体，并保存到map
		 */
		if(bestIchromosome.isEmpty()){
			bestIchromosome.put("chromosome", chromosome);
			bestIchromosome.put("evaluateR", evaluateR);
		}else{
			int eR = (Integer) bestIchromosome.get("evaluateR");
			if(eR<evaluateR){
				bestIchromosome.put("chromosome", chromosome);
				bestIchromosome.put("evaluateR", evaluateR);
			}
		}
		WebLogger.showInfor("评估参数：" + evaluateR);
	}
	
	/**
	 * @See 获得满足条件的染色体种群
	 * @param  questionGeneViewLexicon 试题类型染色体，保存了当前试题类型及指定的试题知识点范围内的所有试题基因视图
	 * */
	public IChromosome[] initChromosomes(QuestionGeneViewLexicon questionGeneViewLexicon){
		List<TreeMap<Integer,Integer>> maxStatusList = new ArrayList<TreeMap<Integer,Integer>>();
		TreeMap<Integer,List<GeneView>> sortMap = getTreeMapInstance();			//保存所有基因按照长度分类后集合
		int chromosomeLength = questionGeneViewLexicon.getGeneViewNumber();		//这里是需要构建的染色体上的基因长度
		List<GeneView> geneViews = (List<GeneView>) questionGeneViewLexicon.getGeneViews();		//这里面保存的是所有基因视图
		//对基因按照长度进行分类保存
		int length = 0;
		List<GeneView> list = null;
		for(GeneView geneView : geneViews){
			length = geneView.getLength();
			if(sortMap.containsKey(length)){
				list = sortMap.get(length);
				if(list==null){
					list = new ArrayList<GeneView>();
				}
				list.add(geneView);
			}else{
				list = new ArrayList<GeneView>();
				list.add(geneView);
				TravelGeneViewInfoUtil.comparatorSortList(list,questionGeneViewLexicon.getGeneViewDifficulty().getIndex());//排序
				sortMap.put(length,list);
			}
		}
		WebLogger.showInfor("完成试题按长度分类");
		TreeMap<Integer,Integer> maxStatus = getTreeMapInstance();		//保存最大状态
		List<Integer> indexs = new ArrayList<Integer>(sortMap.keySet());//sortMap中基因长度的索引
		/**
		 * index是从大到小排序的
		 * 根据基因数量从最大基因长度的基因集合开始减起，可以获得最有可能分解的组合
		 */
		for(Integer index : indexs){
			int count = sortMap.get(index).size();
			int j=0;
			for(;j<count;j++){
				if(index<=chromosomeLength){
					chromosomeLength -= index;
				}else{
					break;
				}
			}
			maxStatus.put(index,j);
		}
		if(chromosomeLength>0){	//如果基因数量不能满足染色体长度要求，此时maxStatusList的长度为0，执行到后面的时候可以检测
			WebLogger.showInfor("错误信息：提供的"+questionGeneViewLexicon.getType()+"试题库的计分单位不满足构建需要，请补充！");
		}else{
			WebLogger.showInfor("提示信息：成功构建最大组合");
			maxStatusList.add(maxStatus);//添加一组
			//分解组合
			this.detailCombination(maxStatus,maxStatusList,sortMap);
		}
		if(maxStatusList.size()>0){//有解实例
			WebLogger.showInfor("***重要提示信息：开始筛选最优染色体***");
			//组合排序'
			List<IChromosome> iChromosomeList = new ArrayList<IChromosome>();
			for(TreeMap<Integer, Integer> treeMap : maxStatusList){//模型：{[1=2,2=3,4=1],[1=4,2=3]}
				/**
				 * 在sortMap中根据基因长度获得指定的基因集合，然后从中随机获取指定数量的基因病构建成染色体返回
				 * treeMap代表一个解集合，即染色体试题
				 */
				//先判断sortMap中是否存在满足条件的试题，即没中基因长度的集合能不能给出指定数量的基因集合
				if(!this.isExistFullGeneView(treeMap,sortMap)){//不满足指定长度的基因数量
					TravelGeneViewInfoUtil.travleMap(treeMap, "淘汰--基因数量不满足构建条件");
				}else{
					//开始在集合中按照试题类型总难度接近度数值获取
					IChromosome iChromosome = this.startBuildChromosomeBySize(treeMap,sortMap);
					if(iChromosome==null){continue;}//基本不可能出现，因为在上面已经进行了排除的操作
					TravelGeneViewInfoUtil.travleMap(treeMap, "合格--参与筛选，等待进化");
					iChromosomeList.add(iChromosome);
				}	
			}
			WebLogger.showInfor("***重要提示信息：结束筛选最优染色体***");
			if(iChromosomeList.size()==0){//一般情况下是不可能的
				return null;
			}
			return iChromosomeList.toArray(new IChromosome[]{});
		}else{
			return null;
		}
	}

	/**
	 * @see 根据分解后的基因组合，在sortMap中随机构建染色体
	 * @return IChromosome 构建成功后的试题类型染色体，如果构建失败就返回null
	 */
	private IChromosome startBuildChromosomeBySize(TreeMap<Integer, Integer> treeMap,
			TreeMap<Integer, List<GeneView>> sortMap) {
		IChromosome chromosome = new IChromosome();
		for(Entry<Integer, Integer> entry : treeMap.entrySet()){
			Integer index = entry.getKey();
			Integer largeSize = entry.getValue();
			List<GeneView> geneView = sortMap.get(index);
			List<GeneView> geneList = geneView.subList(0, largeSize);
			if(geneList == null){
				chromosome = null;
				break;
			}
			chromosome.getGeneViews().addAll(geneList);//将不同基因长度的试题集合加入
		}
		return chromosome;
	}

	/**
	 * @see 检测当前TreeMap对象中要求的不同基因长度的基因数量是否可以从sortMap中得到满足
	 */
	private boolean isExistFullGeneView(TreeMap<Integer, Integer> treeMap,
			TreeMap<Integer, List<GeneView>> sortMap) {
		for(Entry<Integer, Integer> entry : treeMap.entrySet()){
			Integer index = entry.getKey();
			if(entry.getValue()>sortMap.get(index).size()){//如果要求的数量大于实际数量就是不合格的treeMap
				return false;
			}
		}
		return true;
	}

	/**
	 * @param maxStatus 当前需要分解的对象，保存当前染色体实例对象模型, TreeMap<Integer,Integer>中第一个参数表示基因的长度，第二个参数表示基因的数量
	 * @param maxStatusList 保存所有分解成功后的染色体实例对象模型
	 * @param sortMap 保存所有所有基因按照长度分类后集合
	 * @see 递归分解，获取更多模型
	 */
	private void detailCombination(TreeMap<Integer,Integer> maxStatus,List<TreeMap<Integer,Integer>> maxStatusList,TreeMap<Integer,List<GeneView>> sortMap){
		/**
		 * 复制一份保存实例的Map
		 */
		TreeMap<Integer, Integer> treeMap= getTreeMapInstance();
		for(Entry<Integer, Integer> entry : maxStatus.entrySet()){
			treeMap.put(entry.getKey(), entry.getValue());
		}
		/**
		 * 获得不是最后一个又可以分解的基因
		 */
		Integer chromosomeLength = 0;//当期可以分解的基因数量
		boolean isRight = false;//是否可以分解
		for(Entry<Integer, Integer> entry : treeMap.entrySet()){
			if(!isRight){
				int count = entry.getValue();
				if(count>0 && (treeMap.lastEntry()!=entry)){
					treeMap.put(entry.getKey(), count-1);
					chromosomeLength = entry.getKey();
					isRight = true;
				}
			}else{
				//分解保存
				int j=entry.getValue();
				Integer index = entry.getKey();
				int count = sortMap.get(index).size();
				for(;j<count;){
					if(index<=chromosomeLength){
						chromosomeLength -= index;
						j++;
					}else{
						break;
					}
				}
				treeMap.put(index,j);
				if(chromosomeLength>0){
					continue;
				}
				maxStatusList.add(treeMap);//添加一组
				WebLogger.showInfor("提示信息：成功构建一个实体");
				//分解组合
				this.detailCombination(treeMap,maxStatusList,sortMap);
			}
		}
	}

	/**
	 *	@See 用于返回一个TreeMap实例对象，逆序保存
	 */
	private <T> TreeMap<Integer,T> getTreeMapInstance() {
		TreeMap<Integer,T> maxStatus = new TreeMap<Integer,T>(new Comparator<Integer>() {
			@Override
			public int compare(Integer t1, Integer t2) {
				return t2-t1;//从大到小排列
			}
		});
		return maxStatus;
	}
	
	/**
	 * 返回试题类型染色体种群进化后最优秀的染色体
	 */
	public IChromosome getBestChromosome(){
		IChromosome bestSolutionSoFar = null;
		if(bestIchromosome.isEmpty()){
			WebLogger.showInfor("错误信息：尚未生成最优染色体或评估，不能获取最优染色体！");
		}else{
			bestSolutionSoFar =	(IChromosome) bestIchromosome.get("chromosome");
		}
		return bestSolutionSoFar;
	}
	
	/**
	 * 返回试题类型染色体种群进化后最优秀的染色体
	 */
	public List<GeneView> getGeneViewInfos(IChromosome iChromosome){
		return iChromosome.getGeneViews();
	}
	
	/**
	 * @See 通过基因组构建试题染色体、评估各染色体适应度、返回适应度最高的染色体视图的基因组
	 * @param  questionGeneViewLexicon 保存有所有试题基因和当前需要评估的染色体参数信息,不同体型使用时每次都需要重新设置
	 * @return 适应度最高的染色体的基因
	 */
	public List<GeneView> startBulid(QuestionGeneViewLexicon questionGeneViewLexicon) {
		/**
		 * 不需要的情况下
		 */
		if(questionGeneViewLexicon.getGeneViewNumber()<=0){
			return new ArrayList<GeneView>();
		}
		this.questionGeneViewLexicon = questionGeneViewLexicon;
		List<GeneView> geneViewInfos = null;	//最后需要返回的最优基因视图集合
		try {
			/**
			 * 创建并保存当前试题类型染色体实例集合
			 */
			IChromosome[] initedChromosome = this.initChromosomes(questionGeneViewLexicon);	
			/**
			 * 获得各个染色体的评估参数，并保存在map中，每次都要先清空map集合
			 */
			for(IChromosome chromosome : initedChromosome){
				this.evaluate(chromosome);
			}
			/**
			 * 从map中获得最优染色体
			 */
			IChromosome bestChromosome = getBestChromosome();	
			/**
			 * 从最优染色体中得到适应的基因组，即最终试题集合
			 */
			geneViewInfos = this.getGeneViewInfos(bestChromosome);	
		} catch (Exception e) {
			String showMessage = "错误信息：初始化"+questionGeneViewLexicon.getType()+"试题库时出现异常，请联系管理员！";
			WebLogger.showInfor(showMessage);
			throw new NoFullGeneSizeException(showMessage);
		}
		return geneViewInfos;
	}
}


