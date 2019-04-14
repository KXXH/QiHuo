import * as React from 'react';
import { BreadcrumbProps } from '../breadcrumb';
import Tag from '../tag';
export interface PageHeaderProps {
    backIcon?: React.ReactNode;
    prefixCls?: string;
    title: React.ReactNode;
    subTitle?: React.ReactNode;
    style?: React.CSSProperties;
    breadcrumb?: BreadcrumbProps;
    tags?: Tag[];
    footer?: React.ReactNode;
    extra?: React.ReactNode;
    onBack?: (e: React.MouseEvent<HTMLElement>) => void;
    className?: string;
}
declare const PageHeader: React.SFC<PageHeaderProps>;
export default PageHeader;
