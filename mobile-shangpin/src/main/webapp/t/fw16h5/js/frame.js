var Frame = function (times, speed) {
    this.times = times;
    if(times === true){
        this.times = Number.MAX_VALUE
    }
    if(times === false){
        this.times = 0;
    }
    this.speed = 210;
    if(speed){
        this.speed = speed;
    }
    this.stop_signal = false;
};

Frame.prototype.stop = function () {
    this.stop_signal = true;
}

Frame.prototype.play = function(from,to, setp){
    var forward = to > from, index = from, _ = this, hook = null;
    if(!setp || setp === 0){
        setp = 1;
    }
    this.stop_signal = false;
    var _stop = function () {
        window.clearTimeout(hook);
        if (_.onPlayed) {
            _.onPlayed(index);
        }
    }
    function _func() {
        //console.log(from,to, index,(forward ? to + 1 : to - 1));
        if(_.stop_signal){
            _stop();
            return;
        }
        if (index === (forward ? to + setp : to - setp)) {
            if (_.times === 0 || _.stop_signal) {
                _stop();
                return;
            }

            _.times--;
            index = from;
        }

        if (_.onPlaying) {
            _.onPlaying(index);
        }

        index += forward ? setp : -1 * setp;
        //console.log(_.speed);
        hook = window.setTimeout(_func, _.speed);
    }
    hook = window.setTimeout(_func, _.speed);
    return hook;
};