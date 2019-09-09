package com.plugins.infotip;

import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.vfs.VirtualFile;
import com.plugins.infotip.parsing.model.ListTreeInfo;
import com.plugins.infotip.parsing.model.ProjectInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * 目录树显示备注
 *
 * @author LK
 * @date 2018-04-07 1:18
 */
public class TreeOnlyTextProvider implements TreeStructureProvider {

    @NotNull
    @Override
    public Collection<AbstractTreeNode> modify(@NotNull AbstractTreeNode abstractTreeNode, @NotNull Collection<AbstractTreeNode> collection, ViewSettings viewSettings) {
        PsiDirectoryNode(abstractTreeNode);
        return collection;
    }

    @Nullable
    @Override
    public Object getData(Collection<AbstractTreeNode> selected, String dataName) {
        for (AbstractTreeNode abstractTreeNode : selected) {
            PsiDirectoryNode(abstractTreeNode);
        }
        return null;
    }

    /**
     * 获取遍历目录
     * @param abstractTreeNode
     */
    private void PsiDirectoryNode(AbstractTreeNode abstractTreeNode) {
        if (abstractTreeNode instanceof PsiDirectoryNode) {
            ProjectInfo.getParsingConfigureXML(abstractTreeNode.getProject());
            List<ListTreeInfo> listTreeInfos = ProjectInfo.listTreeInfos;
            if (listTreeInfos != null) {
                if (((PsiDirectoryNode) abstractTreeNode).getValue() != null) {
                    VirtualFile pdn = ((PsiDirectoryNode) abstractTreeNode).getValue().getVirtualFile();
                    for (ListTreeInfo listTreeInfo : listTreeInfos) {
                        if (listTreeInfo != null) {
                            if (pdn.getPresentableUrl().equals(listTreeInfo.getPath())) {
                                abstractTreeNode.getPresentation().setLocationString(listTreeInfo.getTitle());
                            }
                        }
                    }
                }
            }
        }

    }
}