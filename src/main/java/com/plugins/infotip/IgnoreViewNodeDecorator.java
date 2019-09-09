/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 hsz Jakub Chrzanowski <jakub@hsz.mobi>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.plugins.infotip;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ProjectViewNodeDecorator;
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.packageDependencies.ui.PackageDependenciesNode;
import com.intellij.ui.ColoredTreeCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.util.ui.UIUtil;
import com.plugins.infotip.parsing.model.ListTreeInfo;
import com.plugins.infotip.parsing.model.ProjectInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.intellij.ui.SimpleTextAttributes.STYLE_SMALLER;

/**
 * 项目目录视图
 *
 * @author LK
 * @date 2018-04-07 1:18
 */
public class IgnoreViewNodeDecorator implements ProjectViewNodeDecorator {

    private static final SimpleTextAttributes GRAYED_SMALL_ATTRIBUTES = new SimpleTextAttributes(STYLE_SMALLER, UIUtil.getInactiveTextColor());

    public IgnoreViewNodeDecorator(@NotNull Project project) {

    }


    @Override
    public void decorate(ProjectViewNode node, PresentationData data) {
        if (node instanceof PsiDirectoryNode) {
            ProjectInfo.getParsingConfigureXML(node.getProject());
            List<ListTreeInfo> listTreeInfos = ProjectInfo.listTreeInfos;
            if (listTreeInfos != null) {
                if (((PsiDirectoryNode) node).getValue() != null) {
                    VirtualFile pdn = ((PsiDirectoryNode) node).getValue().getVirtualFile();
                    for (ListTreeInfo listTreeInfo : listTreeInfos) {
                        if (listTreeInfo != null) {
                            if (pdn.getPresentableUrl().equals(listTreeInfo.getPath())) {
                                data.setLocationString(listTreeInfo.getTitle());
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void decorate(PackageDependenciesNode node, ColoredTreeCellRenderer cellRenderer) {


    }
}
