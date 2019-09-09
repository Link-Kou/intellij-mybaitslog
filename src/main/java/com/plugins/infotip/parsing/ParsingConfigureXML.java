package com.plugins.infotip.parsing;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.XmlRecursiveElementVisitor;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.plugins.infotip.parsing.model.ListTreeInfo;

import static com.plugins.infotip.parsing.model.ProjectInfo.listTreeInfos;

public class ParsingConfigureXML {

    private static final String PATH = "path";
    private static final String SUBPATH = "subpath";
    private static final String PROPERTIES = "properties";
    private static final String CODE = "code";
    private static final String CODEVAL = "codeval";
    private static final String MSG = "msg";
    private static final String TITLE = "title";
    private static final String TOOLTIP = "tooltip";

    /**
     * 解析指定的文件
     * @param project
     * @param filename
     */
    public void Parsing(Project project, String filename) {
        if(project == null){
            return;
        }
        String presentableUrl = project.getPresentableUrl();
        if(presentableUrl == null && presentableUrl.length() == 0){
            return;
        }
        //查询相关文件，与目录无关
        PsiFile[] pfs = FilenameIndex.getFilesByName(project, filename, GlobalSearchScope.allScope(project));
        if (pfs.length == 1) {
            //获取一个文件，如果存在多个相同的文件，取查询到第一个
            PsiFile pf = pfs[0];
            if (pf instanceof XmlFile) {
                listTreeInfos.clear();
                pf.accept(new XmlRecursiveElementVisitor() {
                    @Override
                    public void visitElement(final PsiElement element) {
                        super.visitElement(element);
                        if (element instanceof XmlTag) {
                            //针对节点执行不同的解析方案
                            XmlTag tag = (XmlTag) element;
                            if("trees".equals(tag.getName())){
                                trees(tag, presentableUrl);
                            }
                            if("model".equals(tag.getName())){
                                model(tag, presentableUrl);
                            }
                            if("tree".equals(tag.getName())){
                               tree(tag, presentableUrl);
                            }
                        }
                    }
                });
            }
        }
    }

    private void trees(XmlTag tag, String presentableUrl) {
        //最顶点
        if(tag.getParent() != null){
            PsiElement element = tag.getParent();
            if (element instanceof XmlDocument) {
                ListTreeInfo listTreeInfo = new ListTreeInfo();
                XmlAttribute xmlproperties = tag.getAttribute(PROPERTIES);
                XmlAttribute xmlcode = tag.getAttribute(CODE);
                XmlAttribute xmlcodeval = tag.getAttribute(CODEVAL);
                XmlAttribute xmlmsg = tag.getAttribute(MSG);
                if(xmlproperties!=null && xmlcode!=null && xmlcodeval !=null && xmlmsg!=null){
                    listTreeInfo.setProperties(presentableUrl+xmlproperties.getValue())
                            .setCode(xmlcode.getValue())
                            .setCodeval(xmlcodeval.getValue())
                            .setMsg(xmlmsg.getValue());
                    listTreeInfos.add(listTreeInfo);
                }
            }
        }
    }

    private void model(XmlTag tag, String presentableUrl) {
        ListTreeInfo listTreeInfo = new ListTreeInfo();
        XmlAttribute xmlpath = tag.getAttribute(PATH);
        String xmltitle = tag.getAttribute(TITLE) == null ? "" : tag.getAttribute(TITLE).getValue();
        String xmltooltip = tag.getAttribute(TOOLTIP) == null ? "" :tag.getAttribute(TOOLTIP).getValue();
        XmlAttribute xmlproperties = tag.getAttribute(PROPERTIES);
        XmlAttribute xmlcode = tag.getAttribute(CODE);
        XmlAttribute xmlcodeval = tag.getAttribute(CODEVAL);
        XmlAttribute xmlmsg = tag.getAttribute(MSG);
        String preallpath = getXMLgetParent(tag);
        if(xmlpath != null && xmlproperties!=null && xmlcode!=null && xmlcodeval !=null && xmlmsg!=null){
            String treepath = presentableUrl+preallpath+xmlpath.getValue();
            String treePropertiespath = treepath+xmlproperties.getValue();
            listTreeInfo.setPath(treepath)
                    .setProperties(treePropertiespath)
                    .setCode(xmlcode.getValue())
                    .setCodeval(xmlcodeval.getValue())
                    .setMsg(xmlmsg.getValue())
                    .setTitle(xmltitle)
                    .setTooltip(xmltooltip);
            listTreeInfos.add(listTreeInfo);
        }
    }

    private void tree(XmlTag tag, String presentableUrl) {
        ListTreeInfo listTreeInfo = new ListTreeInfo();
        XmlAttribute xmlpath = tag.getAttribute(PATH);
        String xmltitle = tag.getAttribute(TITLE) == null ? "" : tag.getAttribute(TITLE).getValue();
        String xmltooltip = tag.getAttribute(TOOLTIP) == null ? "" :tag.getAttribute(TOOLTIP).getValue();
        String preallpath = getXMLgetParent(tag);
        if(xmlpath != null){
            String treepath = presentableUrl+preallpath+xmlpath.getValue();
            listTreeInfo.setPath(treepath)
                    .setTitle(xmltitle)
                    .setTooltip(xmltooltip);
            listTreeInfos.add(listTreeInfo);
        }
    }

    /**
     * 递归构建完整目录树
     * @param tag
     * @return
     */
    private String getXMLgetParent(XmlTag tag) {
        PsiElement element = tag.getParent();
        if (element instanceof XmlTag) {
            XmlTag tagp = (XmlTag) element;
            XmlAttribute xmlpath = tagp.getAttribute(PATH);
            XmlAttribute xmlsubpath = tagp.getAttribute(SUBPATH);
            if (xmlpath != null) {
                String pat = xmlpath.getValue() + (xmlsubpath==null?"":xmlsubpath.getValue());
                String parent = getXMLgetParent(tagp);
                parent += pat;
                return parent;
            }
        }
        return "";
    }
}
