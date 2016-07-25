<input id="is-site-master" type=hidden
	value=<%=session.getAttribute("site_master")%> />
<div id='systemGroupView'></div>
<script type="text/javascript">
    if ($('#is-site-master').val() == 't') {
        $("#systemGroupView").load(
                "/configManagement/systemGroupManagementIntegration");
    } else {
        $("#systemGroupView").load(
        "/configManagement/systemGroupManagementNotIntegration");
    }
</script>
