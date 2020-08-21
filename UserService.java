@Service
public class UserService implements OrgService {

    private static final Logger logger = LoggerFactory.getLogger(OrgServiceImpl.class);

    @Autowired
    private OrgMapper orgMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<OrgVo> listTreeOrg() {
        // 1????????????,?????????1
        List<OrgVo> treeOrgs = orgMapper.listTreeOrg();
        Map<String, Object> orgMap = new HashMap<String, Object>();
        List<Map<String, Object>> allSecondOrgResnum  =  orgMapper.getSecondOrgResnum();
        if(allSecondOrgResnum != null){
        	for(Map<String, Object> asor:allSecondOrgResnum){
        		orgMap.put((String) asor.get("orgName"),(long)asor.get("resCount"));
            }
        }
        
        for (OrgVo treeOrg : treeOrgs) {
//            treeOrg.setChildren(listChildren(treeOrg));
            // 2??????????
            List<OrgVo> childOrgs = orgMapper.listChildren(treeOrg.getOrgCode());
            for (OrgVo childOrg : childOrgs){
                // 3??????????????,????
                childOrg.setChildren(listChildren(childOrg));

                // 4????????????????,????
                List<String> orgCodeList = new ArrayList<>();
                orgCodeList.add(childOrg.getOrgCode());
                listChildOrgCode(childOrg.getOrgCode(),orgCodeList);
                // 5??????????????
                
//                int count =  employeeMapper.countCurrentOrgEmp(orgCodeList);
                int allCount  = 0;
                for(String ocl:orgCodeList){
                	if(orgMap.containsKey(ocl)){
                		long count = (long) orgMap.get(ocl);
                		allCount +=count;
                	}
                	
                }
                
                childOrg.setCountEmp(allCount);
            }
            treeOrg.setChildren(childOrgs);
        }
        return treeOrgs;
    }

    /**
     * ??,?????????????
     * @param orgVo
     * @return
     */
    public  List<OrgVo> listChildren(OrgVo orgVo){
        List<OrgVo> childOrgs = orgMapper.listChildren(orgVo.getOrgCode());
        // ????:????????????????
        if (CollectionUtils.isEmpty(childOrgs)){
            return null;
        }
//        int count = 0;
        for (OrgVo childOrg : childOrgs){
            // ????????????
//            count =  employeeMapper.countCurrentOrgEmp(childOrg.getOrgCode());
//            childOrg.setCountEmp(count);
            childOrg.setChildren(listChildren(childOrg));
        }
        return childOrgs;
    }