<template>
    <p class="load-more" style="display: none;">
        <inline-loading></inline-loading>&nbsp;<span style="vertical-align:middle;display:inline-block;font-size:14px;">loading...</span>
    </p>
</template>
<script>
    import { InlineLoading } from 'vux'
    export default {
        components: {
            InlineLoading
        }
    }
</script>
