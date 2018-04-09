<template>
    <button :class="disabled?'sendSmsBtn active':'sendSmsBtn'" type="button" :disabled="disabled">{{ text }}</button>
</template>

<script>
    export default{
        data:function () {
            return {
                time: 60,
                disabled:false,
                text:'获取验证码'
            }
        },
        methods: {
            start: function(){
                this.disabled = true;
                this.timer();
            },
            timer: function () {
                if (this.time > 0) {
                    this.time--;
                    setTimeout(this.timer, 1000);
                    this.text = this.time + 's后重新获取'
                } else {
                    this.disabled = false;
                    this.text = '获取验证码'
                    this.time = 60
                }
            }
        }
    }
</script>

<style>
    .sendSmsBtn{
        position: absolute;
        right: 0px;
        bottom: 0px;
        line-height: 44px;
        border-radius: 3px;
        background: #68bd46;
        border: none;
        color: #fff;
        display: inline-block;
        width: 100px;
        text-align: center;
    }
    .sendSmsBtn.active{
        background: #e1e1e1;
        font-size: 12px;
    }
</style>
