/**
 * Created by zjm97 on 2019/5/26.
 */
function getQueryPath(raw_path){
    return window.location.pathname.replace(window.location.pathname.split('/').pop(),raw_path);
}